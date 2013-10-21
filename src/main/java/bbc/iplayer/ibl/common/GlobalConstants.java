package bbc.iplayer.ibl.common;

public interface GlobalConstants {

	public static final String CONTENT_TYPE_HEADER 				= "Content-Type";
	public static final String MIME_TYPE_APPLICATION_JSON 		= "application/json";
	public static final String MIME_TYPE_APPLICATION_XML 		= "application/xml";
	public static final String MIME_TYPE_APPLICATION_ATOM_XML 	= "application/atom+xml";

	public static final String DEFAULT_CONTENT_TYPE 			= MIME_TYPE_APPLICATION_XML;
	public static final String DEFAULT_ENCODING 				= "UTF-8";

	public static final String DEFAULT_TRUSTSTORE_TYPE 			= "jks";
	public static final String DEFAULT_CERTIFICATE_TYPE 		= "pkcs12";
	
	public static final String SPRING_BEAN_POOLED_CONNECTION_FACTORY_SUFFIX			= "EhCachePooledJmsConnectionFactory";
	public static final String SPRING_BEAN_TOPIC_SUFFIX								= "EhCacheTopic";
	public static final String SPRING_BEAN_DEFAULT_CACHE_REPLICATION_MESSAGE_TTL	= "defaultCacheReplicationMessageTTL";
}
