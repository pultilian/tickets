package tickets.file_dao;

        import java.io.IOException;
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