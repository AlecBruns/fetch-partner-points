# fetch-partner-points

## Building and Running

1. `mvn clean install`
2. `java -jar target/`

## Endpoints

### /add
POST
```
{
    payerRecordList: [
        {
            payer: String
            points: Timestamp
            timestamp: Date
        }
    
    ]
}
```

### /spend
POST
```
{
    "points": int 
}
```

### /get/balance
GET