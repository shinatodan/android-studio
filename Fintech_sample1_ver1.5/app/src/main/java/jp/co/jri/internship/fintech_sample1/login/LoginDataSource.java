package jp.co.jri.internship.fintech_sample1.login;

import android.content.Context;

import java.io.IOException;

import jp.co.jri.internship.fintech_sample1.CsvReader;

public class LoginDataSource {

    public Result<LoggedInUser> login(String userId, String password, Context context) {

        try {
            /* 仮ログイン機能実装として、fakeUserを作成して一律ログイン成功するよう制御 */
            /*LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "フェイクユーザー太郎");
            return new Result.Success<>(fakeUser); //認証結果成功として仮ログインユーザ（fakeUser）の情報をリターン*/

            /*追記*/
            CsvReader parser = new CsvReader();
            parser.readerUserDataBase(context);
            LoggedInUser loggedinuser;
            for(int i = 0; i<parser.userObjects.size();i++){
                if(parser.userObjects.get(i).getUserId().equals(userId)){
                    if(parser.userObjects.get(i).getPassword().equals(password)){
                        loggedinuser = new LoggedInUser(parser.userObjects.get(i).getUserId(),
                                parser.userObjects.get(i).getDisplayName());
                        return new Result.Success<>(loggedinuser);
                    }
                }
            }
            return new Result.Error(new IOException("Error logging"));
            /*追記終わり*/

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e)); //エラー処理：認証結果失敗としてリターン
        }

    }
}
