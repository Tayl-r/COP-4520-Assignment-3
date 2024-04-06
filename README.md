# COP-4520-Assignment-3

Instructions\
Part 1: javac birthdayLinkList.java && java birthdayLinkList\
Part 2: javac TempReading.java && java TempReading\

The efficiency of the program can be evaluated in several aspects. Firstly, the program employs multiple threads to simulate temperature readings from different sensors concurrently. The use of a semaphore (sensor) with a permit count of 8 regulates access to a shared resource (the list of temperature readings). This mechanism ensures that no more than 8 threads can access the shared resource simultaneously, preventing potential race conditions and contention issues. However, one potential inefficiency lies in the sorting of temperature readings. The program keeps track of accurate results from the randomly generated temperatures. The program executes in a reasonable time and will always complete execution due to the correct use of semaphore architecture.
