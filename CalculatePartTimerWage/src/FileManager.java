import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ファイル管理を行う専用クラス
 */
public class FileManager {

    private final String path;

    /**
     * コンストラクタ
     *
     * @param path パス
     */
    public FileManager(String path) {
        this.path = path;
    }

    /**
     * ファイル読み込み
     *
     * @return List<String> ファイルに格納されたデータ
     */
    public List<String> getContent() {

        List<String> content = null;
        Path filePath = Paths.get(this.path);

        try {
            content = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Files#readAllLinesは検査例外なので必ずtry～catch構文を使います。
            System.out.println("[ERROR] ファイルの読み込みに失敗しました。パス : " + path);
            e.printStackTrace();
        }

        return content;
    }
}
