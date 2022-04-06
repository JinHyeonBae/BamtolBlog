TYPE=TRIGGERS
triggers='CREATE DEFINER=`testcase`@`172.%.%.%` trigger postInsertTrigger AFTER insert ON `posts` FOR EACH ROW\nbegin\ninsert into post_information(post_id, user_id) values(new.id, new.user_id);\nend'
sql_modes=1411383296
definers='testcase@172.%.%.%'
client_cs_names='utf8mb4'
connection_cl_names='utf8mb4_general_ci'
db_cl_names='utf8mb4_general_ci'
created=164905993229
