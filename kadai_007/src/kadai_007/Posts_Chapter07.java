package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Connection con = null;
		PreparedStatement statement = null;
		
		//ユーザーリスト
		String[][] userList = {
				{"1003","2023-02-08","昨日の夜は徹夜でした‥","13"},
				{"1002","2023-02-08","お疲れさまでした!","12"},
				{"1003","2023-02-09","今日も頑張ります!","18"},
				{"1001","2023-02-09","無理は禁物ですよ!","17"},
				{"1002","2023-02-10","明日から連休ですね!","20"},
		};
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"keisuke0629"
				);
			System.out.println("データベース接続成功");
			
			
		
			//SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES(?,?,?,?);";
			
			statement = con.prepareStatement(sql);
			
			//リストの1行目から順番に読み込む準
			int rowCnt = 0;
			int total = 0;
			System.out.println("レコード追加を実行します");
			for(int i = 0; i < userList.length; i++) {
				//SQLクエリの「?)部分をリストのデータに置き換え
				statement.setString(1, userList[i][0]);//ユーザーID
				statement.setString(2, userList[i][1]);//投稿日時
				statement.setString(3, userList[i][2]);//投稿内容
				statement.setString(4, userList[i][3]);//いいね数
				
				//SQLクエリを実行(DBMSに送信)
				System.out.println("レコード追加:" + statement.toString());
				rowCnt = statement.executeUpdate();
				total += rowCnt;

			}
			
			System.out.println( total + "件のレコードが追加されました");
			
			//検索
			String kensaku = "SELECT posted_at,post_content,likes FROM posts WHERE user_id = 1002;";
			
			
			//　SQLクエリを実行（DBMSに送信）
			ResultSet result = statement.executeQuery(kensaku);
			
			int id = 1002;
			System.out.println("ユーザーIDが" + id + "のレコードを検索しました");
			
			//SQLクエリを実行結果を抽出
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String content = result.getString("post_content");
				int like = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "/投稿内容=" + content + "/いいね数=" + like);
			}
		}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			//使用したオブジェクトを解放
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException ignore) {}
			}
			if(con != null) {
				try {
					con.close();
				}catch(SQLException ignore) {}
			}
		}
	
	}
}



