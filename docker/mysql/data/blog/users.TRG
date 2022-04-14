TYPE=TRIGGERS
triggers='CREATE DEFINER=`testcase`@`%` trigger insertTrigger\nafter insert on users\nfor each row\nbegin\n	insert into user_information\n    \n    set \n		user_id = new.id;\n    end'
sql_modes=1411383296
definers='testcase@%'
client_cs_names='utf8mb4'
connection_cl_names='utf8mb4_general_ci'
db_cl_names='utf8mb4_general_ci'
created=164882892545
