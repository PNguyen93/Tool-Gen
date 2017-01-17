import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class LowCase {
	private String outputpath;
	private String filename;
	BufferedWriter bw = null;
	FileWriter fw = null;
	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void covertFileName(String name){
		String string = name.toUpperCase().substring(0, 1) + name.toLowerCase().substring(1); 
		setFilename(string);
	}
	public void Generate(String path,String output) throws Throwable, SQLException {
		// Lấy ra đối tượng Connection kết nối vào DB.
		Connection connection = ConnectionUtils.getMyConnection();

		String sql = "select DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = ? and COLUMN_NAME = ?";

		// Please input address file
		boolean first = false;
		String sTotal = "";
		String sName = "";
		try {
			for (String line : Files.readAllLines(Paths.get(path))) {
				if (line.contains("getTableName")) {
					line = line.trim();
					int start = line.indexOf("(");
					int end = line.indexOf(")");
					sName = line.substring(start + 2, end - 1);
					covertFileName(sName);
				}
				if (line.contains("QUALIFIEDCOLUMNS")) {
					first = true;
					continue;
				}
				if (first) {
					if (line.contains("+")) {
						line = line.trim();
						int comman = line.indexOf(",");
						line = line.substring(1, comman + 1);
						sTotal = sTotal + line;
					}
					if (line.contains(";")) {
						break;
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOutputpath(output);
		//write file export
		String dir = getOutputpath() + "\\"+getFilename()+".java";
		System.out.println(dir);
		File f = new File(dir);
		if (f.exists() && !f.isDirectory()) {
			f.delete();
		}
		fw = new FileWriter(dir);
		bw = new BufferedWriter(fw);
		//write end
		String[] split = sTotal.split(",");
		setHeader(sName);
		LinkedHashMap<String, String> hash = new LinkedHashMap<>();
		for (String s2 : split) {
			String type = "";
			// Tạo đối tượng Statement.
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, sName);
			statement.setString(2, s2);
			// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
				String result = rs.getString(1);
				switch (result) {
				case "bigint":
					type = "Long";
					break;
				case "nchar":
					type = "String";
					break;
				case "datetime2":
					type = "Timestamp";
					break;
				case "int":
					type = "Integer";
					break;
				case "numeric":
					type = "BigDecimal";
					break;
				default:
					type = "String";
					break;
				}
			}
			write("\tprivate " + type + " " + s2.toLowerCase() + ";");
			hash.put(s2.toLowerCase(), type);
		}
		setContent(hash);
		// close tag
		write("}");
		System.out.println("Done");
		bw.close();
		// Đóng kết nối
		connection.close();
	}

	private void setContent(LinkedHashMap<String, String> hash) throws IOException {
		Iterator it = hash.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			String key = pair.getKey().toString().toUpperCase().substring(0, 1)
					+ pair.getKey().toString().toLowerCase().substring(1);
			write("\tpublic " + pair.getValue() + " get" + key + "() {");
			write("\t\treturn " + pair.getKey() + ";");
			write("\t}");
			write("\tpublic void set" + key + "(" + pair.getValue() + " " + pair.getKey() + ") {");
			write("\t\tthis." + pair.getKey() + " = " + pair.getKey() + ";");
			write("\t}");
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	private void setHeader(String table) throws IOException {
		// import start
		write("import java.math.BigDecimal;");
		write("import java.sql.Timestamp;");
		write("import javax.persistence.Column;");
		write("import javax.persistence.Entity;");
		write("import javax.persistence.Id;");
		write("import javax.persistence.Table;");
		write("");
		// import end
		// intro start
		write("/**");
		write(" * model class for mapping of table " + table + ".");
		write(" * @author pdo3");
		write(" */");
		// intro end
		// header start
		write("@Entity");
		write("@Table(name = " + '"' + table + '"' + ")");
		write("public class " + getFilename() + " {");
		write("\tpublic " + getFilename() + "() {");
		write("\t}");
		write("\t@Id");
		write("\t@Column(name = " + '"' + "UNIQUE_NUMBER" + '"' + ", unique = true)");
		write("\tprivate Long unique_number;");
		// header end
	}
	private void write(String string) throws IOException {
		bw.write(string);
		bw.newLine();
	}
}
