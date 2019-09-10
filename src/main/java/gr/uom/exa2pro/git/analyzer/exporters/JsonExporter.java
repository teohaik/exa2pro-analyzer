package gr.uom.exa2pro.git.analyzer.exporters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import gr.uom.exa2pro.git.analyzer.RepoFile;
import java.lang.reflect.Type;
import java.util.Collection;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thodoris
 */
public class JsonExporter {
        private final static org.slf4j.Logger logger = LoggerFactory.getLogger(JsonExporter.class.getName());


    public static void exportToJson(Collection<RepoFile> repoFiles) {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        builder.registerTypeAdapter(RepoFile.class, new JsonSerializerImpl());

        Gson customGson = builder.create();
        String customJSON = customGson.toJson(repoFiles);

        System.out.println("<Result_Json_Start>");
        System.out.println(customJSON);
        logger.trace(customJSON);
        System.out.println("<Result_Json_End>");
    }

    private static class JsonSerializerImpl implements JsonSerializer<RepoFile> {

        public JsonSerializerImpl() {
        }

        @Override
        public JsonElement serialize(RepoFile repoFile, Type type, JsonSerializationContext jsc) {
            JsonObject jsonRepoFile = new JsonObject();
            jsonRepoFile.addProperty("file", repoFile.getPath());
            jsonRepoFile.addProperty("edits", repoFile.getCommitCount());
            return jsonRepoFile;
        }
    }
}
