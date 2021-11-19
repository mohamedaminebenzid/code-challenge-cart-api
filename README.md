# Pixartprinting Challenge: Cart API

_*code-challenge-cart-api*_

Cart API Code challenge: part of the interview process at Pixartprinting

_Dear candidate,_

_*We hope you are excited to take this challenge, as much as we will, when reviewing your code*_ :smile:

## Forewords

_*The aim is NOT to see a full working project, production ready, rather to have a glance of your programming skills, approach and ability to solve a problem,*_

Take your time to solve this challenge: time is important but not the most...

You don't need to rush into delivering us a solution, but it can't also take forever.

You don't need to overdo or develop any possible aspect of the application, keep **focus on the most relevant aspects** and those requested by specification.

We will support you in accessing this repo and cloning it

**IMPORTANT:**
As soon as you clone this repo:

- add a CONTRIBUTING.md file push it to your own GitHub repository account, 
- then please share your own repo with your contact person's GithHub account

## Cart Challenge description

We want you to create a REST API to implement the following business requirements collected from our e-commerce Cart use case session.
We are a "web to print" company:

customers connect to our e-commerce, then choose a product, select number of copies to print (quantity) along with a desired delivery date.
Add to Cart. Checkout the Cart.

To have an idea on how we add to cart products, selecting: quantity/date, please have look at a live example: [Product Page](https://www.pixartprinting.co.uk/digital-litho-printing/printing-business-cards/)

### Cart creation

We create a cart by specifying :

`ecommerce_id` , `customer_id`, a `status`, `created_at`, `updated_at`

Cart `status` possible values are : `created`, `building` , `checkout`

### Add Items to Cart

We add items to Cart by providing `ecommerce_id`, `customer_id` and `item_list`

Each entity in `item_list` has the following details:

`product_sku`, `product_name`, `file_type`, `quantity`, `delivery_date`

For this challenge consider:

The `file_type` as a string value indicating the file's MIME type
The `delivery_date` is the choosen date for delivery by a customer
The `quantity` is the number of items for the same product

### View a Cart

The client should be able to query an API endpoint checking what's inside the Cart  

API will return the following details:
Cart `ecommerce_id`, `customer_id`, `created_at` the `status`, eventually a total `price`

The `item_list` with `product_sku`, `product_name`, `file_type`, `quantity`, `delivery_date`.
The calculated `price` for the item based on the policies below.

### Cart checkout

The customer will checkout the cart, the API will set the Cart `date_checkout`
and will calculate and set the final price for the cart

### Price policies calculation

- Let's assume all our products on our catalog have a same standard base price of 1,00€ per item
- When you buy more items of same `product_sku` :
  - no discount is applied for quantities until 100
  - a 5% discount is applied for quantities above 100
  - a 10% discount is applied for quantities above 250
  - a 15% discount is applied for quantities above 500
  - a 20% discount is applied for quantities above 1000
- When the choosen delivery date is (compared to Cart date checkout ):
  - within 24h price increments 30%
  - within 48h price increments 20%
  - within 72h price increments 10%
  - within 1 week price have no increment
- For PDF type formats the price is 15% more than the standard one.
- For PSD type formats (Adobe Photoshop) the price is 35% more than the standard one.
- For AI type formats (Adobe Illustrator) the price is 25% more than the standard one.

## What's desiderable from this project

- Technical stack: You can choose your favourite stack of technologies for this project
- Readability: Create clean code, following standards and good principles of code design. Comment code where's needed.
- Compliance: to the specs
- Testability: easy to understand and test
- Document: Providing a summary of the activity
- Feedback: pls leave us some comments on what you think about the test

**_Have fun coding this challenge!_**

Please let us know when you are done and have committed everything on your GitHub repo

Do not hesitate to contact us for any question about this challenge,
reaching out to your contact person in Pixartprinting!
