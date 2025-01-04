## Export the realm:
* mkdir -p /opt/keycloak/exports
* chmod 777 /opt/keycloak/exports
```shell
   /opt/keycloak/bin/kc.sh export --file /opt/keycloak/exports/test.json --realm <your-realm>
```
## Copy to the project
```shell
docker cp <container_name_or_id>:/opt/keycloak/exports/test.json <the-path-to-the-location>
```

## Avoiding Script upload is disabled error
 remove authorizationSettings property from the realm export.