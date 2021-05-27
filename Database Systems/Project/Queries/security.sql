use zoomDB;

# Restrict writing a message to a meeting when you are not participating in it.
CREATE VIEW write_messages
AS (SELECT (SELECT first_name FROM client WHERE client_id = sender_client_id) AS 'FROM',
           (SELECT IF(receiver_client_id IS null, 'All meeting',
                        (select first_name from client where receiver_client_id=client.client_id))
           ) AS 'TO',
           message,
           messages.last_update AS sent_at
           FROM messages
               JOIN participants ON participants.client_id = messages.sender_client_id);

