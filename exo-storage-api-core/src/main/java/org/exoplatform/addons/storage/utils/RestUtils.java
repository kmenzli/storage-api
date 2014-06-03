package org.exoplatform.addons.storage.utils;

import org.exoplatform.addons.storage.exception.StatisticsException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by menzli on 02/05/14.
 */
public class RestUtils {

    private static final Pattern URL_PATTERN = Pattern
            .compile("^(?i)" +
                    "(" +
                    "((?:(?:ht)tp(?:s?)\\:\\/\\/)?" +                                                       // protolcol
                    "(?:\\w+:\\w+@)?" +                                                                       // username password
                    "(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +  // IPAddress
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|" +     // IPAddress
                    "(?:(?:[-\\p{L}\\p{Digit}\\+\\$\\-\\*\\=]+\\.)+" +
                    "(?:com|org|net|edu|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|asia|cat|coop|int|pro|tel|xxx|[a-z]{2}))))|" + //Domain
                    "(?:(?:(?:ht)tp(?:s?)\\:\\/\\/)(?:\\w+:\\w+@)?(?:[-\\p{L}\\p{Digit}\\+\\$\\-\\*\\=]+))" + // Protocol with hostname
                    ")" +
                    "(?::[\\d]{1,5})?" +                                                                        // port
                    "(?:[\\/|\\?|\\#].*)?$");                                                               // path and query




    //TODO use a service to manage those values instead hard coded them
    private static String [] scopes = {"content", "ALL", "user", "category", "type"};

    private static String isSupportedScope (String scope) {
        if (scope == null) {
            return "content";
        }
        for (String supportedScope : scopes) {
            if (scope.equals(supportedScope)) {
                return supportedScope;
            }
        }
        return null;

    }

    /**
     * Prevents constructing a new instance.
     */
    private RestUtils () {
    }

    public static boolean isValidURL(String link) {
        if (link == null || link.length() == 0) return false;
        return URL_PATTERN.matcher(link).matches();
    }

    /**
     * Gets the media type from an expected format string (usually the input) and an array of supported format strings.
     * If expectedFormat is not found in the supported format array, Status.UNSUPPORTED_MEDIA_TYPE is thrown.
     * The supported format must include one of those format: json, xml, atom or rss, otherwise Status.NOT_ACCEPTABLE
     * could be thrown.
     *
     * @param expectedFormat the expected input format
     * @param supportedFormats the supported format array
     * @return the associated media type
     */
    public static MediaType getMediaType(String expectedFormat, String[] supportedFormats) {

        if (!isSupportedFormat(expectedFormat, supportedFormats)) {
            throw new WebApplicationException(Response.Status.UNSUPPORTED_MEDIA_TYPE);
        }

        if (expectedFormat.equals("json") && isSupportedFormat("json", supportedFormats)) {
            return MediaType.APPLICATION_JSON_TYPE;
        } else if (expectedFormat.equals("xml") && isSupportedFormat("xml", supportedFormats)) {
            return MediaType.APPLICATION_XML_TYPE;
        } else if (expectedFormat.equals("atom") && isSupportedFormat("atom", supportedFormats)) {
            return MediaType.APPLICATION_ATOM_XML_TYPE;
        }
        //TODO What's about RSS format?
        throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
    }

    /**
     * Checks if an expected format is supported not not.
     *
     * @param expectedFormat  the expected format
     * @param supportedFormats the array of supported format
     * @return true or false
     */
    private static boolean isSupportedFormat(String expectedFormat, String[] supportedFormats) {
        for (String supportedFormat : supportedFormats) {
            if (supportedFormat.equals(expectedFormat)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the response object constructed from the provided params.
     *
     * @param entity the identity
     * @param uriInfo the uri request info
     * @param mediaType the media type to be returned
     * @param status the status code
     * @return response the response object
     */
    public static Response getResponse(Object entity, UriInfo uriInfo, MediaType mediaType, Response.Status status) {
        return Response.created(uriInfo.getAbsolutePath())
                .entity(entity)
                .type(mediaType.toString() + "; charset=utf-8")
                .status(status)
                .build();
    }

    public static String computeSearchParameters(String criteria, String scope) throws StatisticsException {


        String expectedScope = isSupportedScope(scope);

        if ( expectedScope.equalsIgnoreCase("ALL")) {

            if (criteria != null) {
                return "content";

            }

            return expectedScope;
        }

        if (criteria != null) {

            return expectedScope;

        }

        throw new StatisticsException(StatisticsException.Code.STATISTICS_VALIDATION_PARAMETER);

    }

    /**
     * Converts a timestamp string to time string by the pattern: EEE MMM d HH:mm:ss Z yyyy.
     *
     * @param timestamp the timestamp to convert
     * @return the time string
     */
    public static final String convertTimestampToDate(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(new Date(timestamp));
    }
}
