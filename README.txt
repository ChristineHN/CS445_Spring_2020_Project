<Configuration Instructions>
Update Package
    $>sudo apt update

Install openjdk-8-jdk
    $>sudo apt install openjdk-8-jdk

Check Java version (1.8)
    $>java -version

Install Maven
    $>sudo apt install maven


<Build and deploy instructions>
    $>mvn install compile test
    $>mvn exec:java

<Copyright and licensing instructions>
Permission is hereby granted, free of change, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute and/or sublicense copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions.
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTIO WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

<Known bugs>
- missing Reports API

<Credits and acknowledgements>
Author: Hyesoo Noh
Contact: hnoh3@hawk.iit.edu

<Memo>
- Link to code repo: https://github.com/ChristineHN/CS445_Spring_2020_Project
- Lines of code: 1269
- Lines of Unit Test: 357
- Unit test coverage: 100% class, 95% method, 80% lines (getter and setter are excluded)
- Cyclomatic Complexity: Max = 14 (RideController.viewAllRide), 6(RateInteractor.rateAccount)
- number of hours to get the code working: 20hours
- number of hours spent preparing the submission: 1.5hour
- list of challenges I faced:
    - unfamiliar with webserver
    - high cyclomatic complexity when dealing with multiple conditions
    - no prior experience with postman
