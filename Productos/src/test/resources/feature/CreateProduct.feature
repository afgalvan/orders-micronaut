Feature: Create a new product
  In order to list and order products
  As a normal user
  I want to register a new product

  Scenario: A new product to be registered
    When I send a POST request to "/api/products" with body:
    """
    {
      "name": "new product",
      "price": 2000,
      "imageUri": "some-temporal-uri"
    }
    """
    Then the response status code should be 201
    And the response should be:
    """
    {
      "id": 1,
      "name": "new product",
      "price": 2000,
      "imageUri": "some-temporal-uri"
    }
    """
