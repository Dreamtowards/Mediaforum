
import 'dart:convert';

import 'package:http/http.dart' as http;

void main() {

  http.post("http://localhost:8080/api/post_getfeedlist", body: json.encode({}), headers: {"Content-Type": "application/json"}).then((resp) {
    print(resp.body);
  });

}