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
package midas.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import midas.domain.Customer;
import midas.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author caio.amaral
 *
 */
@Component
@Path("customer")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@POST
	public Response create(final Customer customer)
			throws URISyntaxException {
		final Customer created = customerService.create(customer);

		return Response.created(new URI("/customer/" + created.getId()))
				.entity(created).build();
	}

	@GET
	@Path("{id}")
	public Customer retrieve(@PathParam("id") final Integer id) {
		return customerService.retrieve(id);
	}

	@PUT
	@Path("{id}")
	public Customer update(@PathParam("id") final Integer id,
			final Customer customer) {
		return customerService.update(id, customer);
	}

	@DELETE
	@Path("{id}")
	public Customer delete(@PathParam("id") final Integer id) {
		return customerService.delete(id);
	}
}
