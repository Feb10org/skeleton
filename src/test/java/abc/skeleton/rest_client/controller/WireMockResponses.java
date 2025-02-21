package abc.skeleton.rest_client.controller;

public final class WireMockResponses {
    public static final String GET_PET_3003_RESPONSE = """
            {
              "id": 3003,
              "category": {
                "id": 1,
                "name": "cats"
              },
              "name": "mat",
              "photoUrls": [
                "http://photo.com/1",
                "http://photo.com/2"
              ],
              "tags": [],
              "status": "available"
            }
            """;

    public static final String ADD_PET_3003_RESPONSE = """
            {
              "id": 3003,
              "category": {
                "id": 1,
                "name": "cats"
              },
              "name": "jerry",
              "photoUrls": [
                "http://photo.com/1",
                "http://photo.com/2"
              ],
              "tags": [],
              "status": "available"
            }
            """;
}
