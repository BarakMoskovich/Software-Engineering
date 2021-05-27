use zoomDB;

-- Country
INSERT INTO `country` VALUES
(1,'Afghanistan')
,(2,'Aland Islands')
,(3,'Albania')
,(4,'Algeria')
,(5,'American Samoa')
,(6,'Andorra')
,(7,'Angola')
,(8,'Anguilla')
,(9,'Antarctica')
,(10,'Antigua and Barbuda')
,(11,'Argentina')
,(12,'Armenia')
,(13,'Aruba')
,(14,'Australia')
,(15,'Austria')
,(16,'Azerbaijan')
,(17,'Bahamas')
,(18,'Bahrain')
,(19,'Bangladesh')
,(20,'Barbados')
,(21,'Belarus')
,(22,'Belgium')
,(23,'Belize')
,(24,'Benin')
,(25,'Bermuda')
,(26,'Bhutan')
,(27,'Bolivia')
,(28,'Bonaire, Sint Eustatius and Saba')
,(29,'Bosnia and Herzegovina')
,(30,'Botswana')
,(31,'Bouvet Island')
,(32,'Brazil')
,(33,'British Indian Ocean Territory')
,(34,'Brunei')
,(35,'Bulgaria')
,(36,'Burkina Faso')
,(37,'Burundi')
,(38,'Cambodia')
,(39,'Cameroon')
,(40,'Canada')
,(41,'Cape Verde')
,(42,'Cayman Islands')
,(43,'Central African Republic')
,(44,'Chad')
,(45,'Chile')
,(46,'China')
,(47,'Christmas Island')
,(48,'Cocos (Keeling) Islands')
,(49,'Colombia')
,(50,'Comoros')
,(51,'Congo')
,(52,'Cook Islands')
,(53,'Costa Rica')
,(54,'Ivory Coast')
,(55,'Croatia')
,(56,'Cuba')
,(57,'Curacao')
,(58,'Cyprus')
,(59,'Czech Republic')
,(60,'Democratic Republic of the Congo')
,(61,'Denmark')
,(62,'Djibouti')
,(63,'Dominica')
,(64,'Dominican Republic')
,(65,'Ecuador')
,(66,'Egypt')
,(67,'El Salvador')
,(68,'Equatorial Guinea')
,(69,'Eritrea')
,(70,'Estonia')
,(71,'Ethiopia')
,(72,'Falkland Islands (Malvinas)')
,(73,'Faroe Islands')
,(74,'Fiji')
,(75,'Finland')
,(76,'France')
,(77,'French Guiana')
,(78,'French Polynesia')
,(79,'French Southern Territories')
,(80,'Gabon')
,(81,'Gambia')
,(82,'Georgia')
,(83,'Germany')
,(84,'Ghana')
,(85,'Gibraltar')
,(86,'Greece')
,(87,'Greenland')
,(88,'Grenada')
,(89,'Guadaloupe')
,(90,'Guam')
,(91,'Guatemala')
,(92,'Guernsey')
,(93,'Guinea')
,(94,'Guinea-Bissau')
,(95,'Guyana')
,(96,'Haiti')
,(97,'Heard Island and McDonald Islands')
,(98,'Honduras')
,(99,'Hong Kong')
,(100,'Hungary')
,(101,'Iceland')
,(102,'India')
,(103,'Indonesia')
,(104,'Iran')
,(105,'Iraq')
,(106,'Ireland')
,(107,'Isle of Man')
,(108,'Israel')
,(109,'Italy')
,(110,'Jamaica')
,(111,'Japan')
,(112,'Jersey')
,(113,'Jordan')
,(114,'Kazakhstan')
,(115,'Kenya')
,(116,'Kiribati')
,(117,'Kosovo')
,(118,'Kuwait')
,(119,'Kyrgyzstan')
,(120,'Laos')
,(121,'Latvia')
,(122,'Lebanon')
,(123,'Lesotho')
,(124,'Liberia')
,(125,'Libya')
,(126,'Liechtenstein')
,(127,'Lithuania')
,(128,'Luxembourg')
,(129,'Macao')
,(130,'Macedonia')
,(131,'Madagascar')
,(132,'Malawi')
,(133,'Malaysia')
,(134,'Maldives')
,(135,'Mali')
,(136,'Malta')
,(137,'Marshall Islands')
,(138,'Martinique')
,(139,'Mauritania')
,(140,'Mauritius')
,(141,'Mayotte')
,(142,'Mexico')
,(143,'Micronesia')
,(144,'Moldava')
,(145,'Monaco')
,(146,'Mongolia')
,(147,'Montenegro')
,(148,'Montserrat')
,(149,'Morocco')
,(150,'Mozambique')
,(151,'Myanmar (Burma)')
,(152,'Namibia')
,(153,'Nauru')
,(154,'Nepal')
,(155,'Netherlands')
,(156,'New Caledonia')
,(157,'New Zealand')
,(158,'Nicaragua')
,(159,'Niger')
,(160,'Nigeria')
,(161,'Niue')
,(162,'Norfolk Island')
,(163,'North Korea')
,(164,'Northern Mariana Islands')
,(165,'Norway')
,(166,'Oman')
,(167,'Pakistan')
,(168,'Palau')
,(169,'Panama')
,(170,'Papua New Guinea')
,(171,'Paraguay')
,(172,'Peru')
,(173,'Phillipines')
,(174,'Pitcairn')
,(175,'Poland')
,(176,'Portugal')
,(177,'Puerto Rico')
,(178,'Qatar')
,(179,'Reunion')
,(180,'Romania')
,(181,'Russia')
,(182,'Rwanda')
,(183,'Saint Barthelemy')
,(184,'Saint Helena')
,(185,'Saint Kitts and Nevis')
,(186,'Saint Lucia')
,(187,'Saint Martin')
,(188,'Saint Pierre and Miquelon')
,(189,'Saint Vincent and the Grenadines')
,(190,'Samoa')
,(191,'San Marino')
,(192,'Sao Tome and Principe')
,(193,'Saudi Arabia')
,(194,'Senegal')
,(195,'Serbia')
,(196,'Seychelles')
,(197,'Sierra Leone')
,(198,'Singapore')
,(199,'Sint Maarten')
,(200,'Slovakia')
,(201,'Slovenia')
,(202,'Solomon Islands')
,(203,'Somalia')
,(204,'South Africa')
,(205,'South Georgia and the South Sandwich Islands')
,(206,'South Korea')
,(207,'South Sudan')
,(208,'Spain')
,(209,'Sri Lanka')
,(210,'Sudan')
,(211,'Suriname')
,(212,'Svalbard and Jan Mayen')
,(213,'Swaziland')
,(214,'Sweden')
,(215,'Switzerland')
,(216,'Syria')
,(217,'Taiwan')
,(218,'Tajikistan')
,(219,'Tanzania')
,(220,'Thailand')
,(221,'Timor-Leste (East Timor)')
,(222,'Togo')
,(223,'Tokelau')
,(224,'Tonga')
,(225,'Trinidad and Tobago')
,(226,'Tunisia')
,(227,'Turkey')
,(228,'Turkmenistan')
,(229,'Turks and Caicos Islands')
,(230,'Tuvalu')
,(231,'Uganda')
,(232,'Ukraine')
,(233,'United Arab Emirates')
,(234,'United Kingdom')
,(235,'United States')
,(236,'United States Minor Outlying Islands')
,(237,'Uruguay')
,(238,'Uzbekistan')
,(239,'Vanuatu')
,(240,'Vatican City')
,(241,'Venezuela')
,(242,'Vietnam')
,(243,'Virgin Islands, British')
,(244,'Virgin Islands, US')
,(245,'Wallis and Futuna')
,(246,'Western Sahara')
,(247,'Yemen')
,(248,'Zambia')
,(249,'Zimbabwe');

-- City
-- 108 Israel
INSERT INTO `city`
VALUES
(1, 108, 'Or-Akiva'),
(2, 108, 'Ashkelon'),
(3, 108, 'Bat-Yam'),
(4, 108, 'Dimona'),
(5, 108, 'Eilat'),
(6, 108, 'Givatayim'),
(7, 108, 'Hadera'),
(8, 108, 'Haifa'),
(9, 108, 'Holon'),
(10, 108, 'Kfar-Saba'),
(11, 108, 'Lod'),
(12, 108, 'Modi`in'),
(13, 108, 'Netanya'),
(14, 108, 'Petah-Tikva'),
(15, 108, 'Rishon-LeZion'),
(16, 108, 'Tel-Aviv'),
(17, 108, 'Yavne');

-- Address
INSERT INTO `address` VALUES
(1, 8,  'Oskar Schindler 14', 'Apt. 27', 'Haifa', '37000', NOW()),
(2, 14, 'Habnim 3', 'Central', NULL, '39020', NOW()),
(3, 16, 'Rothschild 87', 'Apt. 4', 'Central', '31111', NOW()),
(4, 16, 'Rothschild 114', 'Apt. 9', 'Central', '31222', NOW()),
(5, 15, 'Lilac 7', 'Central', NULL, '34044', NOW());

-- Subscription
INSERT INTO `subscription` VALUES
(1, 'Free', 0), -- Duration 0 - Limit to 1 Hour
(2, 'Paid', 1); -- Duration 1 - Unlimited

-- Client
INSERT INTO `client` VALUES
(1, 1, 2, 'Barak', 'Moskovich', 'Barak.Moskovich@s.afeka.ac.il', 'Online', '123456', now()),
(2, 2, 2, 'Denis', 'Karabitski', 'Denis.Karabitski@s.afeka.ac.il', 'Online', '123456', now()),
(3, 3, 2, 'Dor', 'Cohen', 'dorchoen@gmail.com', 'Away', '123456', now()),
(4, 4, 2, 'Jack', 'Sparrow', 'iamJackSparrow@gmail.com', 'Offline', '123456', now()),
(5, 5, 2, 'Mila', 'Kunis', 'MilaKunis123@gmail.com', 'Offline', '123456', now());

-- Contacts
INSERT INTO `contacts` VALUES
(1, 2, now()),
(1, 3, now()),
(1, 4, now()),
(1, 5, now()),
(2, 3, now()),
(2, 4, now()),
(2, 5, now()),
(3, 5, now());

-- Exposure
INSERT INTO `exposure` VALUES
('1', 'Private'),
('2', 'Public'),
('3', 'Unlisted');

-- Channels
INSERT INTO `channels` VALUES
(1, 1, 'ProjectDB', now()),
(2, 2, 'Strong Staff', now());

-- Channel Members
INSERT INTO `channel_members` VALUES
(1, 1, now()),
(1, 2, now()),
(2, 1, now()),
(2, 2, now()),
(2, 4, now()),
(2, 5, now());

-- Meeting
INSERT INTO `meeting` VALUES
(1, 'PresentationDB', now(), '256', NULL, now()),
(2, 'History Class', now(), '256', NULL, now()),
(3, 'Pirates of the Caribbean', now(), '10', NULL, now());

-- Permissions
INSERT INTO `permissions` VALUES
('1', 'Host'),
('2', 'Co-Host'),
('3', 'Viewer');

-- Participants
INSERT INTO `participants` VALUES
(1, 1, 1, now()),
(2, 1, 2, now()),
(3, 2, 1, now()),
(4, 3, 1, now()),
(5, 3, 3, now());

-- Messages
INSERT INTO `messages` VALUES
(1, 1, NULL, 'Hey', now()),
(2, 2, NULL, 'Hey :)', now()),
(3, 1, 2, 'Are you ready ?', now()),
(4, 4, NULL, 'Go Pirates of the Caribbean!', now()),
(5, 5, 4, 'Who is?', now()),
(6, 4, 5, 'Johnny Depp!', now());