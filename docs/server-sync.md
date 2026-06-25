# Remka Server Sync

Remka uses a zero-knowledge sync shape for the first server version:

1. The Android app serializes the local snapshot.
2. The Android app encrypts it on the phone with Android Keystore.
3. The server receives only the encrypted payload.
4. The server stores the payload as a blob and cannot read vehicle data.

Do not commit real server addresses, tokens, SSH logins, passwords, or private keys.

## Android configuration

Add local values to `local.properties`. This file is ignored by Git.

```properties
remka.sync.url=http://YOUR_SERVER_IP:8080
remka.sync.token=CHANGE_ME_TO_A_LONG_RANDOM_TOKEN
```

For production, use HTTPS and remove cleartext HTTP later.

## Server start

Set environment variables on the server:

```bash
export REMKA_PORT=8080
export REMKA_DATA_DIR=/opt/remka/data
export REMKA_SERVER_TOKEN=CHANGE_ME_TO_A_LONG_RANDOM_TOKEN
```

Start the server:

```bash
./gradlew :server:run
```

Health check:

```bash
curl http://127.0.0.1:8080/health
```

The sync endpoint stores encrypted blobs:

```bash
PUT /sync/{accountId}
GET /sync/{accountId}
```

If `REMKA_SERVER_TOKEN` is set, clients must send:

```text
Authorization: Bearer CHANGE_ME_TO_A_LONG_RANDOM_TOKEN
```
