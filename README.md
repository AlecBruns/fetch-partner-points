# Fetch Payers Transactions App

## Building and Running

1. `mvn clean install`
2. `java -jar target/payers-transactions-0.0.1-SNAPSHOT.jar`

## Example Requests

### /add
POST
```
{
    "payerRecordList": [
       { "payer": "Hello", "points": 500, "timestamp": "2020-09-02T14:00:00Z" },
       { "payer": "Wonder", "points": 43, "timestamp": "2020-10-02T14:00:00Z" },
       { "payer": "Wonder", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" },
       { "payer": "DANNON", "points": 6, "timestamp": "2020-12-02T14:00:00Z" }

    ]
}
```

### /spend
POST
```
{
    "points": 500
}
```

### /get/balance
GET