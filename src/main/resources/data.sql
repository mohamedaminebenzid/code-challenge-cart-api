insert into product(id,sku,name,price) values(1,'PBC0001','Paper Business Cards',1.0);
insert into product(id,sku,name,price) values(2,'IC0001','Invitation Cards',1.0);
insert into product(id,sku,name,price) values(3,'FL0001','Flyers and Leaflets',1.0);
insert into product(id,sku,name,price) values(4,'DPTC0001','Digitally Printed T-shirts',1.0);
insert into product(id,sku,name,price) values(5,'HB0001','Hardback books',1.0);
insert into product(id,sku,name,price) values(6,'PB0001','Pillow Boxes',1.0);
insert into product(id,sku,name,price) values(7,'FB0001','Fabric Banners',1.0);


insert into customer(id,last_name,first_name,username) values (1,'Aretini','Nicola','naretini');
insert into customer(id,last_name,first_name,username) values (2,'Ben Hmida','Imed','ibenhmida');

--insert into cart(id,customer_id,status,creation_date,update_date,checkout_date,price) values(1,1,'CHECKOUT','2022-11-30 10.59.59','2022-12-31 18.59.59','2022-12-31',100.05);
--insert into cart(id,customer_id,status,creation_date,update_date,checkout_date,price) values(2,1,'CREATED','2022-12-30 12.59.59','2022-12-31 14.59.59','2022-12-31',NULL);

--insert into cart(id,customer_id,status,creation_date,update_date,checkout_date,price) values(3,2,'CHECKOUT','2022-11-30 10.59.59','2022-12-31 18.59.59','2022-12-31',200.05);
--insert into cart(id,customer_id,status,creation_date,update_date,checkout_date,price) values(4,2,'CREATED','2022-12-30 12.59.59','2022-12-31 14.59.59','2022-12-31',NULL);
--
--insert into cart_item (id,cart_id,product_id,quantity,delivery_date,file_type) values(1,1,1,100,'2022-12-30','PDF');
--insert into cart_item (id,cart_id,product_id,quantity,delivery_date,file_type) values(2,1,2,150,'2022-12-30','AI');
--insert into cart_item (id,cart_id,product_id,quantity,delivery_date,file_type) values(3,1,3,250,'2022-12-30','PSD');
--insert into cart_item (id,cart_id,product_id,quantity,delivery_date,file_type) values(4,1,4,500,'2022-12-30','PDF');
--insert into cart_item (id,cart_id,product_id,quantity,delivery_date,file_type) values(5,1,5,1000,'2022-12-30','PDF');