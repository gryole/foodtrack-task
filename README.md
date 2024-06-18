# Getting Started

Prerequisites `Java 17`

Run application locally on port `8080`:

```
./gradlew bootRun
```

Use can use postman collection `postman/FoodTruck.json` to test API

# About the project

Application contains .csv file with open food truck data.
CSV file is parsed on application start and loads data to memory.
API provides few HTTP endpoint to use data:

- `/foodtrucks` returns all food trucks
- `/foodtrucks/foodItems` returns all food categories that food trucks provide, can be used in search
- `/foodtrucks/search` can be used to search food trucks by applicant, food item, by coordinated in given point and
  radius

# Build and run docker image
```
./gradlew bootBuildImage --imageName=engineering-assessment/food-track-api-v1
```

```
docker run -p 8080:8080 engineering-assessment/food-track-api-v1
```
# Possible improvements 
- Add layered/onion architecture with architecture tests. It takes some time to setup, but make clear borders between part of appplication
- Change format of input data. CSV could be little problematic to parse, I noticed on open data resource option to use json. Maybe defining model and using json parser can be better from clean code perspective and easier to sync by fetching data online
- Keeping data in memory is fast solution for showcase task, but adding persistent layer would be beneficial. 
- Add sync with open data source. We can fetch fresh data online and have the latest food tuck updates. 
- Add more tests. I tried to cover major functionality, but few more test could be added.   
- Work little bit on data parsing, some fields contains strange values. Add rules to remove data that is not aligned with other records
- Add api spec doc. Update project to provide open api spec and swagger ui
- Add authorization and implement users
- Add some features related to users - comments, rating, list of visited trucks etc