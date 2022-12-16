## Technical Stack

* Open JDK 11
* H2 database
* SpringBoot 2.7.5 (spring starter web, spring starter data, spring starter hateoas)
* Built with Maven 3.8 

## Running the Application

- Install Open JDK 11

- There is no need to install maven as we use maven wrapper included in the project

- git clone the project

- Run the following command in a terminal window (within the project's root folder) : mvnw spring-boot:run 

- Once the application has started :

  - we could access to H2 DB console through: http://localhost:8080/h2-console/ 
   with the following login/password:cart/cart

  - we could access to Swagger(Open API) Rest API Documentation : http://localhost:8080/swagger-ui/index.html
   through which you can read the full documentation and test all the implemented REST methods as well

## Rest API documentation
Please refer to Swagger UI (see above)	

## Nominal Use case (Happy path)

To make this test simple please use swagger-ui (see above).

- call GET /cart-api/v1/customers/ and pick one customer's username (for example "naretini")

- create new cart for the picked customer by calling POST /cart-api/v1/customers/{customer-username}/carts 
with the path variable customer-username = "naretini" (without a request body)

- call GET /cart-api/v1/products/ and pick the name of some products(product name is unique)
to be added later to the created cart, for example we choose 'Paper Business Cards', 'Digitally Printed T-shirts', 'Pillow Boxes'

- add items (as much as you like) to the cart created above(cart-id=1) 
by calling POST /cart-api/v1/carts/{cart-id}/items

	Request body :
	{
	  "productName": "Paper Business Cards",
	  "fileType": "PDF",
	  "quantity": 500,
	  "deliveryDate": "2022-12-20"
	}

- you can delete a cart item by calling : DELETE /cart-api/v1/items/{item-id}

- you could view the current cart(cart-id=1)  by calling : GET /cart-api/v1/carts/{cart-id}

- once you finish editing the cart (cart-id=1), you could checkout it by calling :
 POST /cart-api/v1/carts/{cart-id}/checkout

## Import notes
- It's noteworthy that the implemented REST API correspond to the level 3 of the Richardson Maturity Model
  by supporting Hypermedia as the Engine of Application State (HATEOAS).
 
  "With HATEOAS, a client interacts with a network application whose application servers provide information dynamically through hypermedia. A REST client needs little    to no prior knowledge about how to interact with an application or server beyond a generic understanding of hypermedia"(Wikipedia)

- I have used POST rather than PUT for the checkout operation as it is not idempotent

- To create the cart we had to choose between two options: we either lazily create the cart the first time we’re asked to put a product in it or we eagerly create it     with a separate call before we can put any products in it.

	Pros of option 1 :
	 - one less call
	 - do not create a shopping cart for a user that will never put anything in it

	Pros of Option 2:
	 - More clear
	 - Compliant to specifications
 
 The option 2 has been implemented
 

- I have used some DTOs to isolate the domain models from the presentation

- To make the checkout operation Restful we may change it to :

	PATCH /cart-api/v1/carts/{cart-id}
	
	{
	  "status": "CHECKOUT"
	}

- I did not get the point behind the cart status BUILDING. 
  So I assume it is an intermediary status between CREATED and CHECKOUT.
  A cart has the status BUILDING only if it has at least one item and the checkout operation has not be called on it.

## Errors

### 404 Not Found
When a resource (cart, product, customer, cart item) has not been found

### 405 Method Not Allowed
- When you attempt to checkout a cart that is in the status CHECKOUT or CREATED

- When you attempt to add/delete/edit an item from a cart that is in the status CHECKOUT

## Future enhancements

- add unit/integration/E2E tests

- add security layer

- add pagination/sorting/filtering to product listing

- make price calculation service open to extension closed to modification

## Feedbacks

- I liked the fact that you can use your favourite technical stack and you could focus on the most relevant aspects

- I did not get the point behind the cart status BUILDING

- At first glance, the challenge looks easy but I guess there are some pitfalls :)
  I'm looking forward to know your point of view about the identifiable requests for core development that should be addressed


