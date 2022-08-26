# http-trace

http-trace will provide a custom actuator endpoint */actuator/httptrace*. This endpoint will return a JSON array with
the last n (configurable) requests and responses.

See https://zgheb.com/i?v=blog&pl=86 for more information

## Configuration

You will have to set *httptrace.enabled* to true, otherwise none of the beans will be loaded.

The optional parameter *bodyCutOff* (defaulting to 100) defines after how many characters, the request / response bodies
will be truncated (memory usage!)

The optional parameter *capacity* (defaulting to 10) defines how many requests and responses will be kept in memory and
thus accessible from the actuator. If the capacity is reached, the oldest entry will be purged from memory.

```yaml
httptrace:
  enabled: true
  bodyCutOff: 100
  capacity: 10
```
