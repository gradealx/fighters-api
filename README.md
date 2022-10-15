# Fighters API

Java Spring and Docker playground for trying out things :)

The project is a CRUD-like application consisting of an API (Java Spring) and a database (MongoDB).

Further documentation can be found [here](https://google.com).

## How-to-start

The preferred way to run the application is **Docker**.

In the root folder of the project, run `docker-compose up --build` to pull the required images and start all services.

This will start three Docker containers:

- mongo_fightclub: The service that runs a Docker container with the MongoDB instance.
- mexpress_fightclub: The service that runs a Docker container with an instance of Mongo Express. This is a web-based
  administrative interface for MongoDB. It can be accessed via http://localhost:8081/. The local username and password
  is **mexpress**. When opening the web interfaced, the fighter_db that is used for this application can be accessed.
- api: The service that runs a Docker container with the Java Spring application that serves as an API.

When starting the application, 9 sample fighters will be added to the database.

## Features

The API provides the following endpoints:

`GET /fighters` With this endpoint one can fetch all fighters from the database.

sample CURL: curl --location --request GET 'http://localhost:8080/fighters'

`GET /fighters/{fighterId}` With this endpoint one can fetch a specific fighter from the database. If the fighter does
not exist, a 404 error is thrown.

sample CURL: curl --location --request GET 'http://localhost:8080/fighters/a'

`POST /battles` This endpoint is designed to receive the client fighters that are 'sent' to a battles. If
less than 5 fighters are provided by the client, a 404 error is thrown. Furthermore, the validity of the fighters is
checked. If too many fighters are 'out of order', a 404 error is thrown. Therefore, at least 5 **valid** fighters have
to be provided by the client. If more than 5 valid fighters are provided, the fighters are sorted by their experience,
and the 5 '
best' fighters are sent to the battle.

With those 5 fighters a battle is then simulated. Each fighter battles one member of the opposing team. Therefore, each single client fighter can be winner or loser. It is determined weather the fighter is winner
or loser randomly, though, the higher the fighters' experience the greater the chances that they are winner of a battle.
The logic for this is implemented in BattleService: simulateBattle(). If a fighters wins a battle, their experience is
increased by 10, if they lose, it is decreased by 10. The respective change in experience is updated in the database
fighters collection.

All battles that took place are then persisted in a battle collection in the database. A persisted battle datastructure
has the form of

{ <br>
"fighterId": "the id of the fighter",<br>
"date": "Date of the battle",<br>
"winner": true if the fighter won the battle<br>
}

sample CURL: curl --location --request POST 'http://localhost:8080/battles' \
--header 'Content-Type: application/json' \
--data-raw '["a","b","c","d","e","f","g","h"]'

`GET /battles` With this endpoint one can fetch all battles that previously took place.

sample CURL: curl --location --request GET 'http://localhost:8080/battles'

`GET /leaderboard` With this endpoint one can get the current ranking that is dependent on all battles that previously
took place. Therefore, a leaderboard that contains each fighter which participated in a battle is created. The ranking
is dependent on the previous wins/loses of a fighter, meaning the fighter with most total wins will be on the first
place. To calculate the ranking, a LeaderCalculator datastructure is used. This class maintains two maps:
fighterNumberWinsMap and winsPlaceMap. fighterNumberWinsMap maps the id of a fighter to the number of wins that it has.
winsPlaceMap maps the number of wins to a certain place in the ranking. This way it can be considered that multiple
fighters (which have the same number of wins) are on the same place. Furthermore, in a proper ranking, when we have for
example two instances in first place, there is no second place but only a third one. All variations of this scenario are
also considered.

The leaderboard that is returned has the structure of:

{<br>
"name": "name of the fighter",<br>
"place": place in the ranking<br>
}

sample CURL: curl --location --request GET 'http://localhost:8080/leaderboard'

A postman collection with all sample requests can be found in: fighters api.postman_collection.json
