USE zoomDB;

DELIMITER $$

CREATE DEFINER=`root`@`localhost` TRIGGER `time_limit_meeting_trigger` AFTER INSERT ON `participants` FOR EACH ROW BEGIN
	-- Declare variables
	DECLARE CurrParticipantID INT;
	DECLARE CurrMeetingID INT;
    DECLARE HostPermissionID INT;
    DECLARE HostSubscription INT;
    DECLARE CurrSubscription INT;
    DECLARE TimeLimit INT;
    
    -- Set data into variables
    SET TimeLimit = NULL;
    SET CurrParticipantID = new.client_id;
    SET CurrMeetingID = new.meeting_id;
    SELECT permission_id INTO HostPermissionID FROM permissions WHERE permission = 'Host';
    
    IF (SELECT permission_id 
		FROM participants
        WHERE client_id = CurrParticipantID) = HostPermissionID
	THEN
		SELECT s.subscription_id INTO CurrSubscription FROM client c
		JOIN subscription s on c.subscription_id = s.subscription_id
		WHERE c.client_id = CurrParticipantID;
    
		SELECT subscription_id INTO HostSubscription FROM subscription WHERE subscription = 'free';
    
		IF (CurrSubscription = HostSubscription)
        THEN
			SET TimeLimit = '1';
        END IF;
            
		UPDATE meeting
		SET time_limit = TimeLimit
		WHERE meeting_id = new.meeting_id;
	END IF;
END

$$ DELIMITER ;


DELIMITER $$

/* if host called delete all messages for each user */
CREATE TRIGGER remove_participant
    before DELETE
    ON participants FOR EACH ROW
    BEGIN

        DECLARE message_amount INT;
        SET message_amount = (SELECT COUNT(*) FROM messages WHERE receiver_client_id = old.client_id OR sender_client_id = old.client_id);
        if(@delete_all_participants IS NOT NULL) THEN
            msg_loop: LOOP
                IF(message_amount != 0) then
                    DELETE FROM messages
                        WHERE receiver_client_id = old.client_id OR sender_client_id = old.client_id;

                    SET message_amount = message_amount - 1;
                    ITERATE msg_loop;
                ELSE
                    LEAVE msg_loop;
                end if;
            end loop;
        end if;

    end;

CREATE TRIGGER delete_meeting BEFORE DELETE
    ON meeting FOR EACH ROW
    BEGIN
        DECLARE members_count INT;

        IF @delete_all_participants IS NOT NULL then
            -- there is a user that has Host permission and he closes the meeting
            SET members_count = (SELECT COUNT(*) FROM participants
                                    WHERE participants.meeting_id = old.meeting_id);

            participant_loop: LOOP
                if (members_count != 0) then
                    delete from participants
                        where old.meeting_id = participants.meeting_id;
                    SET members_count = members_count - 1;
                    ITERATE participant_loop;
                ELSE
                    LEAVE participant_loop;
                end if;
            end loop;
        end if;
    end;


CREATE PROCEDURE remove_meeting_and_participants(IN meeting_id INT, IN permission_id INT)
BEGIN
    /* double check for permission Host if you would like to remove a meeting and all its content */
    IF(permission_id = (SELECT permission_id AS perID FROM permissions WHERE permission = 'Host')) THEN
        SET @delete_all_participants = 1;
        DELETE FROM meeting
            WHERE meeting_id = meeting.meeting_id;
    end if;
    SET @delete_all_participants = NULL;
END;

-- CALL TO REMOVE PARTICIPANT
CREATE PROCEDURE remove_participant(IN pID INT)
BEGIN
    DECLARE mID INT;
    DECLARE permission_ID INT;

    SET mID = (SELECT meeting_id FROM participants WHERE pID = client_id);
    SET permission_ID = (SELECT permission_id FROM participants WHERE client_id = pID);

    IF(permission_ID = (SELECT permission_id AS perID FROM permissions WHERE permission = 'Host')) THEN
        CALL remove_meeting_and_participants(mID, permission_ID);
    ELSE
        DELETE FROM participants
            WHERE client_id = pID;
    end if;
END;


-- deletes client (who is a host)
CALL remove_participant((SELECT client_id from participants
                                WHERE participants.client_id =
                                      (SELECT client_id FROM client WHERE first_name = 'Barak' AND last_name = 'Moskovich')
                            )
                        );


-- deletes client (who is not a host)
CALL remove_participant((SELECT client_id from participants
                                WHERE participants.client_id =
                                      (SELECT client_id FROM client WHERE first_name = 'Denis' AND last_name = 'Karabitski')
                            )
                        );
                        
$$ DELIMITER ;