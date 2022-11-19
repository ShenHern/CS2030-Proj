# CS2030 Project - Discrete Event Simulation
Discrete event simulation for a customer/server checkout system.
## Events
- Arrive: a customer arrives at the checkout
- Wait: a customer waits a a checkout counter
- Serve: a customer is served by a checkout counter
- Leave: a customer leaves if all checkout counters are full
- Done: a customer is done being served by a checkout counter
- Buffer: buffer event between a wait and serve

## Possible Sequence of Events
- `Arrive -> Serve -> Done`
- `Arrive -> Wait -> Buffer -> Serve -> Done` (customer queues at checkout if none are immediately available)
- `Arrive -> Leave` (customer leaves if all queues are full)
# User input:
- Input file `test.in`
- User input starts with values representing the number of servers, the number of self-check counters, the maximum queue length, the number of customers and the probability of a server resting. This is followed by the arrival times of the customers. Lastly, a number of service times (could be more than necessary) are provided.

# Output:
- A log of events
- A summary of simulation statistics is printed at the bottom. They represent average wait time, the number of customers served, and the number of customers left respectively.