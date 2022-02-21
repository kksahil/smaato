Implementation approach and design considerations

Requirements :

1. Build a Java application - REST service which is able to process 10K requests per second.
2. The service has one GET endpoint - /api/smaato/accept which is able to accept an integer id as a mandatory query parameter and an optional string HTTP endpoint query parameter. It should return String “ok” if there were no errors processing the request and “failed” in case of any errors.
3. Every minute, the application should write the count of unique requests your application received in that minute to a log file. Uniqueness of request is based on the id parameter provided.
4. When the endpoint is provided, the service should fire an HTTP GET request to the provided endpoint with count of unique requests in the current minute as a query parameter. Also log the HTTP status code of the response.


Solution Overview :

1. Create Spring boot Application using maven or gradle (this sample application is using maven).
2. Generate One Rest Controller Class which will have two GET endpoints, one for counting unique request IDs and second for displayng the total count of unique IDs.
3. Generate a Quartz scheduler Job which will read the counts from a HashMap collection then write it to the file as log and clear the Map collection for collecting new request IDs for next minute.
4. This Quartz Scheduler Job will run through out the Application Life cycle i.e starting from the Application till the shutdown of Application. The scheduler triggers every minute.
5. Two endpoints in a Rest Controller class will be used to write and read from the HashMap collection.



Files in an Application :

CounterRest.java : Rest Controller class in which two endpoints are defined to read and write the HaspMap Collection which is declared as :

public static volatile Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

We can put all the logic along with collection into a seperate class but for the sake of simplicity I have put it in the same Rest Controller which is handling the user requests.

LogJob.java : Quartz Job class which will read the static collection and write the total count in a log file every minute and then clear the collection for counting for next minute.

QuartzConfig.java : Used to register JobDetail Bean and Quartz Trigger Bean for triggering the job every minute.

requestcount.log : log file used to write logs.

logback-spring.xml : configuration related to logging file.


Two endpoints are :

http://localhost:8080/api/smaato/accept?id=1&str=AnyStringOrWithoutString

http://localhost:8080/api/smaato/getcurrentcount


Solutions for Additional Problems :


Extension 1: Instead of firing an HTTP GET request to the endpoint, fire a POST request. The data structure of the content can be freely decided.

Solution : We can create PostMapping method in Rest Controller to handle encoded post request. The logic will be the same as Get methods.


Extension 2: Make sure the id deduplication works also when the service is behind a Load Balancer and 2 instances of your application get the same id simultaneously.

Solution : We can use in-memory Database such as Redis Server to create Map collection (same as HaspMap defined in Rest Controller). Every instance of application which behind the load balancer write and read same Map collection in the Redis server.

Extension 3: Instead of writing the count of unique received ids to a log file, send the count of ids to a distributed streaming service of your choice.

Solution : One such thing can be done through Kafka. 



