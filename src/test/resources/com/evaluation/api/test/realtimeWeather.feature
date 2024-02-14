Feature: Validating Weather API

    Scenario Outline: Verify the key is successfully searched using Weather API
        Given I have a Realtime Weather API client
        When I search the "<key>" with a GET request
        Then the API responded with status code "<statusCode>"
        And the API response body has expected schema
        And the API response body has expected city "<city>" and country "<country>"
        Examples:
            | key            | statusCode | city   | country                  |
            | Miami          | 200        | Miami  | United States of America |
            | EC2Y 5AS       | 200        | London | UK                       |
            | 48.8567,2.3508 | 200        | Paris  | France                   |

    Scenario Outline: Verify the error message when location not matched
        Given I have a Realtime Weather API client
        When I search the "<key>" with a GET request
        Then the API responded with status code "<statusCode>"
        And the API response body has expected error code <errorCode> and message "<errorMessage>"
        Examples:
            | key        | statusCode | errorCode | errorMessage                |
            | dsdasdasda | 400        | 1006      | No matching location found. |

    Scenario Outline: Verify if the authentication of Weather API
        Given I have a Realtime Weather API client with wrong credentials
        When I search the "<key>" with a GET request
        Then the API responded with status code "<statusCode>"
        Examples:
            | key   | statusCode |
            | Miami | 403        |
