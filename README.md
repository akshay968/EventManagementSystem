Event Management System<br />
This API service is built on Spring boot with Mongodb Database.<br />

API Endpoints:<br />
    1. GetMapping("events/find")  Parameters- String startDate, Double latitude, Double longitude,@(required = false, defaultValue = "0") int page,(required=false, defaultValue = "10") int size)<br />
    
     2. @PostMapping("/addEvent")   Parameters- @RequestBody Event event<br />
     3.@PostMapping(value = "/events/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) Parameters- (@RequestParam("file") MultipartFile file) this file can be send in through Postman.<br />
     4.@GetMapping("/") "Welcome to EventManagement APIService"<br />
     5.@GetMapping("weather")(@RequestParam String city,@RequestParam String date) return weather String<br />
     6.@GetMapping("distance") ( double latitude1,  double longitude1, double latitude2,double longitude2) return distance<br />

MongoDb: <br />
  Because there are no joins involved (won't make Nosql slow), and there are longitudes and latitudes we can have geospatial indexing on them when needed.<br />
  
	Collections : <br />
  public class Event {<br />
	private String event_name;<br />
	private String city_name;<br />
	private String date;<br />
	private String time;<br />
	private Double latitude;<br />
	private Double longitude;<br />
 }<br />
<br />
 400 Bad Request<br />
 <br />
 for every api which has date in it ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use yyyy-MM-dd format")<br />
 for every api which haslongitude and latitude  in it ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid latitude or longitude values")<br />
 for any api any reqiured parameter is missing ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required parameters missing");<br />
500 Internal server error<br />
ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");<br />
<br />
 Live Deployed Service Link<br />
https://eventmanagementsystem-9du6.onrender.com/<br />
  <br />
 Sample Request through browser<br />
 findevent API<br />
 https://eventmanagementsystem-9du6.onrender.com/events/find?startDate=2024-03-01&longitude=-740.0060&latitude=40.7128%20&page=1<br />
 distance API<br />
 https://eventmanagementsystem-9du6.onrender.com/distance?latitude1=40.7128&longitude1=-74.0060&latitude2=25.5169968004073&longitude2=-173.22570039222800<br />
 weather API<br />
 https://eventmanagementsystem-9du6.onrender.com/weather?city=Port Rebeccaberg&date=2024-03-01<br />
 events/upload<br />
  https://eventmanagementsystem-9du6.onrender.com/events/upload<br />
  for this we can submit csv file through postman otherwise we get 500 Internal server error<br />
<br />
 How to Setup:<br />
 step 1: git clone  https://github.com/akshay968/EventManagementSystem<br />
 step 2: cd EventManagementSystem<br />
 step3: mvn clean install<br />
 To run:<br />
 step 4: mvn spring-boot:run<br />




        
