package persistence;

import org.json.JSONObject;

// Interface used for persistence
public interface Writable {
    // EFFECTS: returns this as JSON object

    JSONObject toJson();
}
