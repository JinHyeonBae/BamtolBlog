package db.migration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V3__Insert_Dummy_User extends BaseJavaMigration{

    @Override
    public void migrate(Context context) throws Exception {
        
        try(Statement insert = context.getConnection().createStatement()){
            insert.executeUpdate("INSERT INTO users(nickname) values('spring_good')");
            try(Statement select = context.getConnection().createStatement()){
                try(ResultSet rows = select.executeQuery("select id from users where nickname='spring_good'")){
                    if(rows.next()){
                        int user_id = rows.getInt(1);
                        String name = "jane";
                        String nickname = "spring_good";
                        String email = "1234@naver.com";
                        String password = "1234";

                        try(Statement insert_dual = context.getConnection().createStatement()){
                            insert_dual.executeUpdate("INSERT INTO user_information (name ,nickname, email,password, user_id) VALUES("
                                + "'" + name + "'" + "," + "'" + nickname + "'" +"," + "'" + email + "'"+ "," + "'" + password +
                                "'" + "," + "'" + user_id + "'" +  ")"
                            );
                        }
                    }
                }
            }
        }
    }
}