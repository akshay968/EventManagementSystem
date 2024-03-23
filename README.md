Event Management System
This API service is built on Spring boot with Mongodb Database.

API Endpoints:
	   1. GetMapping("events/find")  Parameters- String startDate, Double latitude, Double longitude,@(required = false, defaultValue = "0") int page,(required=false, defaultValue = "10") int size)
     2. @PostMapping("/addEvent")   Parameters- @RequestBody Event event
     3.@PostMapping(value = "/events/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) Parameters- (@RequestParam("file") MultipartFile file) this file can be send in through Postman.
     4.@GetMapping("/") "Welcome to EventManagement APIService";
	   5.@GetMapping("weather")(@RequestParam String city,@RequestParam String date) return weather String
     6.@GetMapping("distance") ( double latitude1,  double longitude1, double latitude2,double longitude2) return distance

MongoDb: 
  Because there are no joins involved (won't make Nosql slow), and there are longitudes and latitudes we can have geospatial indexing on them when needed.
  
	Collections : 
  public class Event {
	private String event_name;
	private String city_name;
	private String date;
	private String time;
	private Double latitude;
	private Double longitude;
 }

 400 Bad Request
 
 for every api which has date in it ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use yyyy-MM-dd format")
 for every api which haslongitude and latitude  in it ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid latitude or longitude values")
 for any api any reqiured parameter is missing ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required parameters missing");
500 Internal server error
ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");

 Live Deployed Service Link
https://eventmanagementsystem-9du6.onrender.com/
  
 Sample Request through browser
 findevent API
 https://eventmanagementsystem-9du6.onrender.com/events/find?startDate=2024-03-01&longitude=-740.0060&latitude=40.7128%20&page=1
 distance API
 https://eventmanagementsystem-9du6.onrender.com/distance?latitude1=40.7128&longitude1=-74.0060&latitude2=25.5169968004073&longitude2=-173.22570039222800
 weather API
 https://eventmanagementsystem-9du6.onrender.com/weather?city=Port Rebeccaberg&date=2024-03-01
 events/upload
  https://eventmanagementsystem-9du6.onrender.com/events/upload
  for this we can submit csv file through postman otherwise we get 500 Internal server error

 How to Setup:
 step 1: git clone  https://github.com/akshay968/EventManagementSystem
 step 2: cd EventManagementSystem
 step3: mvn clean install
 To run:
 step 4: mvn spring-boot:run




        
