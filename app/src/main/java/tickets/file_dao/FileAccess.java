package tickets.server.dataAccess.FileDAO;

        import java.io.File;
        import java.io.IOException;
        import java.nio.charset.Charset;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.nio.file.StandardOpenOption;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class FileAccess {
    private static final String OUTFOLDER = "files/";

    public void checkpointUpdate(String object, String type, String id) throws IOException {

    }

    public void removeFile(String type, String id) throws IOException {

    }

    public void addUpdate(String command, String type, String id) throws IOException {

    }

    public List<String> getDeltasForObject(String type, String id) throws IOException {
        return null;
    }

    public List<String> getAllObjects(String type) throws IOException {
        return null;
    }

    public void removeAllFiles() throws IOException {

    }
}