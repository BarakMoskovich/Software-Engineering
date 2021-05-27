USE zoomdb;

-- 1) Show all friends for a specific user.
SELECT (SELECT first_name FROM client WHERE client.client_id = contacts.client1_id) AS name FROM contacts
WHERE client2_id = (SELECT client_id FROM client WHERE first_name = 'Denis')
UNION ALL
SELECT (SELECT first_name FROM client WHERE client.client_id = contacts.client2_id) AS name  FROM contacts
WHERE client1_id = (SELECT client_id FROM client WHERE first_name = 'Denis')
ORDER BY name;

SELECT (SELECT     c1.first_name
        FROM     client c1
        WHERE     c1.client_id = client2_id AND c1.client_id != c.client_id
        OR         c1.client_id = client1_id AND c1.client_id != c.client_id) AS NAMES
FROM     contacts
JOIN     client c on first_name = 'Denis'
WHERE     client1_id = c.client_id AND client2_id != c.client_id
OR         client2_id = c.client_id AND client1_id != c.client_id
ORDER BY NAMES;

-- 2) Show all friends for a specific user that live in Israel. todo
SELECT (SELECT   c1.first_name
        FROM     client c1
        JOIN address ADDR on ADDR.address_id = c1.address_id
        JOIN city CITY on CITY.city_id = ADDR.city_id
        JOIN country COUNTRY on COUNTRY.country_id = CITY.country_id
        WHERE     (c1.client_id = client2_id AND c1.client_id != c.client_id
        OR         c1.client_id = client1_id AND c1.client_id != c.client_id) AND country = 'Israel') AS NAMES
FROM     contacts
JOIN     client c on first_name = 'Denis'
WHERE     client1_id = c.client_id AND client2_id != c.client_id
OR         client2_id = c.client_id AND client1_id != c.client_id
ORDER BY NAMES;

-- 3) Show all channels for a specific user.
SELECT name FROM channels
JOIN channel_members cm on channels.channel_id = cm.channel_id
JOIN client c on c.client_id = cm.client_id
WHERE c.first_name = 'Denis';

-- 4) Show all participants in a single meeting.(By meeting_id)
SELECT c.first_name FROM meeting
JOIN participants p on meeting.meeting_id = p.meeting_id
JOIN client c on c.client_id = p.client_id
WHERE p.meeting_id = 1;


-- 5) Show how many meetings are there that have more than 10 participants in it. (Admin)
SELECT DISTINCT COUNT(DISTINCT p1.client_id) AS COUNT_PARTICIPANTS FROM participants AS p1, participants as p2
WHERE p1.meeting_id = p2.meeting_id
GROUP BY p1.meeting_id
HAVING COUNT_PARTICIPANTS > 10;


-- 6) List all mutual Channels for 2 participants in the same meeting. (Admin)
SELECT name FROM channels
JOIN channel_members cm on channels.channel_id = cm.channel_id
WHERE client_id IN ((SELECT client_id FROM client WHERE client.first_name = 'Denis')
                        AND
                    (SELECT client_id FROM client WHERE client.first_name = 'Barak'));


-- 7) Show meetings name and number of participants that have at least 2 people in it. (Admin)
SELECT title, COUNT(DISTINCT p.client_id) AS COUNTER FROM meeting
JOIN participants p on meeting.meeting_id = p.meeting_id
CROSS JOIN participants p2 on meeting.meeting_id = p2.meeting_id
WHERE p.meeting_id = p2.meeting_id
GROUP BY title
HAVING COUNTER >= 2;

-- 8) Show the longest meeting that is currently running. (Admin)
SELECT title, last_update FROM meeting
ORDER BY last_update DESC
LIMIT 1;

-- 9) Show meeting name for all contact from a specific user friend list.
SELECT (SELECT     c1.first_name
        FROM     client c1
        WHERE     c1.client_id = client2_id AND c1.client_id != c.client_id
        OR         c1.client_id = client1_id AND c1.client_id != c.client_id) AS NAMES,
       (SELECT title FROM meeting
           JOIN participants p on meeting.meeting_id = p.meeting_id
           WHERE P.client_id = client2_id) as title
FROM     contacts
JOIN     client c on first_name = 'Denis'
WHERE     client1_id = c.client_id AND client2_id != c.client_id
OR         client2_id = c.client_id AND client1_id != c.client_id
ORDER BY NAMES;

-- 10) Show all public Channels.
SELECT name FROM channels
JOIN exposure e on e.exposure_id = channels.exposure_id
WHERE exposure = 'Public';

-- 11) Show all users that are from United States.
SELECT client_id, first_name, last_name FROM client
JOIN address a on a.address_id = client.address_id
JOIN city c on c.city_id = a.city_id
JOIN country c2 on c2.country_id = c.country_id
WHERE c2.country = 'United States';

-- 12) Show all mutual friends between two users.
SELECT (SELECT first_name FROM client WHERE client.client_id = contacts.client1_id) AS name FROM contacts
WHERE client2_id = (SELECT client_id FROM client WHERE first_name = 'Barak')
UNION 
SELECT (SELECT first_name FROM client WHERE client.client_id = contacts.client2_id) AS name  FROM contacts
WHERE client1_id = (SELECT client_id FROM client WHERE first_name = 'Denis')
ORDER BY name;

-- 13) Show all mutual Channels between two users.
SELECT (SELECT name FROM channels WHERE C1.channel_id = channels.channel_id) AS channel_name FROM channel_members AS C1
WHERE client_id = 1 AND C1.channel_id IN
                        (SELECT channel_id FROM channel_members AS C2
                        WHERE C2.client_id = 2)