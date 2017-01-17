import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerConnUtils_JTDS {

	// Kết nối vào SQLServer.
	// (Sử dụng thư viện điều khiển JTDS)
	public static Connection getSQLServerConnection() throws SQLException, ClassNotFoundException {
		List<String> list = new ArrayList<String>();
		try {
			// load screen
			String workingDir = System.getProperty("user.dir");
			String FILENAME = workingDir + "\\logDB.txt";
			BufferedReader br = null;
			FileReader fr = null;
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(FILENAME));
			while ((sCurrentLine = br.readLine()) != null) {
				list.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String hostName = list.get(0); //20.203.133.154
		String sqlInstanceName = "MSSQLSERVER";
		String database = list.get(1); //INT77DB2014_TG2_PDO3_21122016
		String userName = list.get(2); //pdo3
		String password = list.get(3); //vm1dta12#$

		return getSQLServerConnection(hostName, sqlInstanceName, database, userName, password);
	}

	// Trường hợp sử dụng SQLServer.
	// Và thư viện JTDS.
	public static Connection getSQLServerConnection(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		// Khai báo class Driver cho DB SQLServer
		// Việc này cần thiết với Java 5
		// Java6 tự động tìm kiếm Driver thích hợp.
		// Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		// Cấu trúc URL Connection dành cho SQLServer
		// Ví dụ:
		// jdbc:jtds:sqlserver://localhost:1433/simplehr;instance=SQLEXPRESS
		String connectionURL = "jdbc:jtds:sqlserver://" + hostName + ":1433/" + database + ";instance="
				+ sqlInstanceName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

}