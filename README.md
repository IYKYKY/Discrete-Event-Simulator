# Discrete-Event-Simulator
The project simulates how customers are being served by human servers with a random resting time and self-checkout counters without resting time.

User input starts with values representing the number of servers, the number of self-check counters, the maximum queue length and the probability of a server resting. This is followed by the arrival times of the customers. Lastly, a number of service times (could be more than necessary) are provided.

As a result, the similation run will provide users with the information of how the customers are served(can be arrives -> serves -> done; arrives -> leaves; arrives -> waits -> serves -> done), as well as keep track of the average waiting time for customers who have been served; the number of customers served; the number of customers who left without being served.
(p.s. you may try to run the program by typing java Main < 1.in with the five test cases provided)
