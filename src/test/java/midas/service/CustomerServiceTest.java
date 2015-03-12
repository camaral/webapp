/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package midas.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import midas.domain.Customer;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test expects that the server is up and running. TODO: Init the server
 * automatically
 * 
 * @author caio.amaral
 *
 */
public class CustomerServiceTest {

	private static CustomerServiceApi customerService;

	@BeforeClass
	public static void setup() {
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final ResteasyWebTarget target = client
				.target("http://localhost:9095/");

		customerService = target.proxy(CustomerServiceApi.class);
	}

	@Test
	public void testCreate() {
		Customer customer = new Customer();
		customer.setFirstName("caio");
		customer.setLastName("amaral");

		Response response = customerService.create(customer, true);

		Assert.assertEquals(201, response.getStatus());
		Assert.assertNotNull(response.getHeaderString(HttpHeaders.LOCATION));

		Customer created = response.readEntity(Customer.class);

		Assert.assertNotNull(created.getId());
		Assert.assertEquals("caio", created.getFirstName());
		Assert.assertEquals("amaral", created.getLastName());
	}

	@Test
	public void testRetrieve() {
		Customer customer = new Customer();
		customer.setFirstName("Caio");
		customer.setLastName("Amaral");

		Response response = customerService.create(customer, true);
		Customer created = response.readEntity(Customer.class);

		Assert.assertNotNull(created.getId());

		Customer found = customerService.retrieve(created.getId());

		Assert.assertEquals(created.getId(), found.getId());
		Assert.assertEquals("Caio", found.getFirstName());
		Assert.assertEquals("Amaral", found.getLastName());
	}

	@Test
	public void testUpdate() {
		Customer customer = new Customer();
		customer.setFirstName("Caio");
		customer.setLastName("Amaral");

		Response response = customerService.create(customer, true);
		Customer created = response.readEntity(Customer.class);

		Integer id = created.getId();

		Assert.assertNotNull(id);

		created.setFirstName("Kyle");
		Customer updated = customerService.update(id, created, true);

		Assert.assertEquals(id, updated.getId());
		Assert.assertEquals("Kyle", updated.getFirstName());
		Assert.assertEquals("Amaral", updated.getLastName());

		Customer found = customerService.retrieve(id);

		Assert.assertEquals(id, found.getId());
		Assert.assertEquals("Kyle", found.getFirstName());
		Assert.assertEquals("Amaral", found.getLastName());
	}

	@Test
	public void testDelete() {
		Customer customer = new Customer();
		customer.setFirstName("Caio");
		customer.setLastName("Amaral");

		Response response = customerService.create(customer, true);
		Customer created = response.readEntity(Customer.class);

		Integer id = created.getId();

		Assert.assertNotNull(id);

		Customer deleted = customerService.delete(id);

		Assert.assertEquals(created.getId(), deleted.getId());
		Assert.assertEquals("Caio", deleted.getFirstName());
		Assert.assertEquals("Amaral", deleted.getLastName());

		try {
			customerService.delete(id);
			Assert.fail("Should have thrown NotFoundException");
		} catch (NotFoundException ex) {
			// OK
		}
	}

	@Test
	public void testRetrieveNotFound() {
		try {
			customerService.retrieve(-10);
			Assert.fail("Should have thrown NotFoundException");
		} catch (NotFoundException ex) {
			// OK
		}
	}

	@Test
	public void testUpdateNotFound() {
		try {
			customerService.update(-10, new Customer(), true);
			Assert.fail("Should have thrown NotFoundException");
		} catch (NotFoundException ex) {
			// OK
		}
	}

	@Test
	public void testUpdateDontChangeId() {
		Customer customer = new Customer();
		customer.setFirstName("Caio");
		customer.setLastName("Amaral");

		Response response = customerService.create(customer, true);
		Customer created = response.readEntity(Customer.class);

		Integer id = created.getId();

		Assert.assertNotNull(id);

		created.setId(id + 100);
		created.setFirstName("Kyle");
		Customer updated = customerService.update(id, created, true);

		Assert.assertEquals(id, updated.getId());
		Assert.assertEquals("Kyle", updated.getFirstName());
		Assert.assertEquals("Amaral", updated.getLastName());

		Customer found = customerService.retrieve(id);

		Assert.assertEquals(id, found.getId());
		Assert.assertEquals("Kyle", found.getFirstName());
		Assert.assertEquals("Amaral", found.getLastName());
	}
}

@Path("customer")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
interface CustomerServiceApi {

	@POST
	public Response create(final Customer customer,
			@QueryParam("reload") @DefaultValue("false") final boolean reload);

	@GET
	@Path("{id}")
	public Customer retrieve(@PathParam("id") final Integer id);

	@PUT
	@Path("{id}")
	public Customer update(@PathParam("id") final Integer id,
			final Customer customer,
			@QueryParam("reload") @DefaultValue("false") final boolean reload);

	@DELETE
	@Path("{id}")
	public Customer delete(@PathParam("id") final Integer id);
}
