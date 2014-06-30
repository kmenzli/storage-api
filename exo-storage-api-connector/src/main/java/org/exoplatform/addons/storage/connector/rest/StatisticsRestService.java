package org.exoplatform.addons.storage.connector.rest;

import org.exoplatform.addons.storage.exception.StatisticsException;
import org.exoplatform.addons.storage.listener.GuiceManager;
import org.exoplatform.addons.storage.model.StatisticsBean;
import org.exoplatform.addons.storage.services.StatisticsService;
import org.exoplatform.addons.storage.utils.RestUtils;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by menzli on 02/05/14.
 */
@Path("/statistics/api/")
public class StatisticsRestService implements ResourceContainer {



    private static final Log LOG = ExoLogger.getLogger(StatisticsRestService.class);

    StatisticsService statisticsService = null;


    private StatisticsList statistics = null;

    private static String[] mediaTypes = new String[] { "json", "xml" };

    private  StatisticsList mockStatistics = null;

    public StatisticsRestService() {

        this.statisticsService = GuiceManager.getInstance().getInstance(StatisticsService.class);

        mockStatistics =  new StatisticsList();

    }

    @GET
    @Path("/cleanup")
    @RolesAllowed("administrators")
    public Response cleanupStatistics(@Context UriInfo uriInfo) throws Exception {

        try {

            statisticsService.cleanupStatistics(0);

        } catch (Exception E) {

            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

        }

        return RestUtils.getResponse(mockStatistics, uriInfo, MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);

    }


    @POST
    @Path("/add/")
    //@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed("users")
    public Response addEntry(@Context UriInfo uriInfo,
                             @FormParam("username") String username,
                             @FormParam("from") String from,
                             @FormParam("type") String type,
                             @FormParam("category") String category,
                             @FormParam("categoryId") String categoryId,
                             @FormParam("content") String content,
                             @FormParam("link") String link,
                             @FormParam("site") String site,
                             @FormParam("siteType") String siteType) throws Exception  {

        StatisticsBean statisticsBean = null;

        try {

            statisticsBean = statisticsService.addEntry(username,from,type,category,categoryId,content,link,site,siteType);

        } catch (Exception E) {

            LOG.error("Cannot add statistic ", E);

            return RestUtils.getResponse(null, uriInfo, MediaType.APPLICATION_JSON_TYPE, Response.Status.INTERNAL_SERVER_ERROR);

        }

        return RestUtils.getResponse(statisticsBean, uriInfo, MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);

    }


    @GET
    @Path("/query.{format}")
    @RolesAllowed("users")
    public Response query(@Context UriInfo uriInfo,
                           @QueryParam("criteria") String criteria,
                           @DefaultValue("ALL") @QueryParam("scope") String scope,
                           @DefaultValue("10") @QueryParam("offset") String offset,
                           @DefaultValue("100") @QueryParam("limit") String limit,
                           @DefaultValue("1") @QueryParam("sort") String sort,
                           @DefaultValue("1") @QueryParam("order") String order,
                           @PathParam("format") String format) throws Exception {

        MediaType mediaType = RestUtils.getMediaType(format, mediaTypes);

        List<StatisticsBean> statisticBOs = null;

        statistics =  new StatisticsList();


        try {

            //String searchScope = RestUtils.computeSearchParameters(criteria,scope);

            statisticBOs = statisticsService.query(criteria, scope, Integer.parseInt(offset), Integer.parseInt(limit), Integer.parseInt(sort), Integer.parseInt(order), 0);

            statistics.setStatistics(statisticBOs);

        } catch (StatisticsException E) {

            LOG.warn("The search parameters used has not been filled out correctly please check your request, scope ["+scope+"] and criteria ["+criteria+"]");

            return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);


        } catch (Exception E) {

            LOG.warn("The search parameters used has not been filled out correctly please check your request, scope ["+scope+"] and criteria ["+criteria+"]");

            return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);

        }

        return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);
    }

    @GET
    @Path("/filter.{format}")
    @RolesAllowed("users")
    public Response filter(@Context UriInfo uriInfo,
                           @QueryParam("user") String user,
                           @QueryParam("category") String category,
                           @QueryParam("categoryId") String categoryId,
                           @QueryParam("type") String type,
                           @QueryParam("site") String site,
                           @QueryParam("siteType") String siteType,
                           @QueryParam("content") String content,
                           @QueryParam("timestamp") String timestamp,
                           @PathParam("format") String format) throws Exception {

        MediaType mediaType = RestUtils.getMediaType(format, mediaTypes);

        List<StatisticsBean> statisticBOs = null;

        statistics =  new StatisticsList();


        try {

            statisticBOs = statisticsService.filter(user, category, categoryId, type, site, siteType, content,true, Integer.parseInt(timestamp));

            statistics.setStatistics(statisticBOs);

        } catch (StatisticsException E) {

            LOG.warn("The filter parameters has not been filled out correctly please check your request, user ["+user+"], category ["+category+"], categoryId["+categoryId+"], type["+type+" and timestamp["+timestamp+"]");

            return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);


        } catch (Exception E) {

            LOG.warn("The filter parameters has not been filled out correctly please check your request, user ["+user+"], category ["+category+"], categoryId["+categoryId+"], type["+type+" and timestamp["+timestamp+"]");

            return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);

        }

        return RestUtils.getResponse(statistics, uriInfo, mediaType, Response.Status.OK);
    }


    @GET
    @Path("/export/.{format}")
    @RolesAllowed("administrators")
    public Response exportStatistics() throws Exception {
        throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
    }

    @GET
    @Path("/import/.{format}")
    @RolesAllowed("administrators")
    public Response importStatistics() throws Exception {
        throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
    }

    @XmlRootElement
    static public class StatisticsList {

        private List<StatisticsBean> _statistics;

        /**
         * sets statistics list
         *
         * @param statistics list
         */
        public void setStatistics(List<StatisticsBean> statistics) {
            _statistics = statistics;
        }

        /**
         * gets statistics list
         *
         * @return statistics list
         */
        public List<StatisticsBean> getStatistics() {
            return _statistics;
        }

        /**
         * adds space to space list
         *
         * @param statisticsBean
         * @see org.exoplatform.addons.storage.model.StatisticsBean
         */
        public void addStatisticBO(StatisticsBean statisticsBean) {
            if (_statistics == null) {
                _statistics = new LinkedList<StatisticsBean>();
            }
            _statistics.add(statisticsBean);
        }
    }

}
