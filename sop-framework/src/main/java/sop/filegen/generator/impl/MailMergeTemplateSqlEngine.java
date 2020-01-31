package sop.filegen.generator.impl;

//import com.ibm.db2.jcc.uw.h;
//import com.pccw.oframework.exception.SqlTemplateInitException;
//import com.pccw.oframework.util.io.WildCardFilter;

/**
 * MailMergeTemplateSqlEngine is an Engine for generate the Docx
 * 1.get the SQL by screen id and template id
 * 2.if the SQL with the template is common, can put in to screen id "common"
 * when engine cannot found the sql by screen id and template will search screen id "common"
 *
 * @author Gary
 */
public class MailMergeTemplateSqlEngine {
//	//Key:schema+SQLID
//	//Value:SQL
//	private static Map<String, String> sqlMap;
//	//Key:screenID+templateID
//	//Value:sqlID+SQL
//	private static Map<String, Map<String, String>> sqlCache;
//	//Key:schema
//	//Value:List<String> SQL
//	private static Map<String, Map<String, String>> commonSqlMap=new HashMap<String, Map<String, String>>();
//
//	private Map<Integer,Map<String,Object>> SqlCommonParams;
//	private static final Logger logger = LoggerFactory.getLogger(MailMergeTemplateSqlEngine.class);
//
//	private static Object mutex = new Object();
//
//	private static MailMergeTemplateSqlEngine instance;
//
//	private MailMergeTemplateSqlEngine() {
//		super();
//	}
//
//	public static MailMergeTemplateSqlEngine getInstance() throws SqlTemplateInitException {
//		return getInstance(FileGeneratorUtils.getMailMergeSQLPath());
//
//
//	}
//
//	@SuppressWarnings("unused")
//	public synchronized static MailMergeTemplateSqlEngine getInstance(String sqlFolder) throws SqlTemplateInitException {
//		//sqlFolder="C:/Document/CSSS2/CSSSII-dev/pom.swd.config/config/mailMerge/sqlTemplates/";
//		synchronized (mutex) {
//			if (instance != null) {
//			//if (false) {//just for test
//				if (logger.isTraceEnabled()) {
//					logger.trace("returning singleton MailMergeTemplateSqlEngine.");
//				}
//				return instance;
//			} else {
//
//				logger.info("Initiating MailMergeTemplateSqlEngine.");
//				instance = new MailMergeTemplateSqlEngine();
//				File dir = new File(sqlFolder);
//				logger.debug("SQL folder:{}", sqlFolder);
//				if (dir.isDirectory()) {
//					try{
//					//cache SQL file
//						instance.cacheSQLFromSQLXML(dir);
//					}catch(Exception e){
//						logger.error("error parse directory.", e);
//					}
//					//cache mapping file
//
//					File[] files = dir.listFiles(new WildCardFilter("mailMerge-*.xml"));
//
//						logger.debug("getting sql in production mode.");
//						sqlCache = new HashMap<String, Map<String, String>>();
//
//
//						for (File file : files) {
//
//							if (file.getName().endsWith(".xml") && file.getName().startsWith("mailMerge-")) {
//								logger.info("Resolving sql xml file {}", file.getName());
//								try {
//									Document tdoc = instance.read(file);
//									String[] value1= file.getName().split("-");
//									String sche1=value1[1];
//									String[] value2=sche1.split("\\.");
//									String schema=value2[0];
//
//									instance.cacheTsqls(schema,tdoc,sqlFolder);
//								} catch (MalformedURLException e) {
//									throw new SqlTemplateInitException("SQL template initiation error!", e);
//								} catch (DocumentException e) {
//									throw new SqlTemplateInitException("SQL template initiation error!", e);
//								}
//							} else {
//								logger.info("File {} will be ignored by MailMergeTemplateSqlEngine, please make sure xml file starts with 'mailMerge-' and end with '.xml'.", file.getName());
//							}
//						}
//					}
//
//				logger.info("SqlTemplateEngine initialization completed.");
//
//				return instance;
//			}
//
//		}
//
//
//	}
//
//
//	private  void cacheSQLFromSQLXML(File dir) {
//		sqlMap=new HashMap<String, String>();
//
//		if (dir.isDirectory()) {
//			File[] files = dir.listFiles(new WildCardFilter("mailMergeSQL-*.xml"));
//				logger.debug("getting sql in production mode.");
//				for (File file : files) {
//					String[] value1= file.getName().split("-");
//					String sche1=value1[1];
//					String[] value2=sche1.split("\\.");
//					String sche2=value2[0];
//					if (file.getName().endsWith(".xml") && file.getName().startsWith("mailMergeSQL-") ) {
//						logger.info("Resolving sql xml file {}", file.getName());
//						try {
//							Document tdoc = read(file);
//							cacheSQL2Map(sche2,tdoc);
//						} catch (MalformedURLException e) {
//							throw new SqlTemplateInitException("SQL template initiation error!", e);
//						} catch (DocumentException e) {
//							throw new SqlTemplateInitException("SQL template initiation error!", e);
//						}
//					} else {
//						logger.info("File {} will be ignored by MailMergeTemplateSqlEngine, please make sure xml file starts with 'mailMergeSQL-' and end with '.xml'.", file.getName());
//					}
//				}
//			}
//	}
//
//	private void cacheSQL2Map(String schema,Document tdoc) {
//		Map<String, String> sqlCommon=new HashMap<String, String>();
//
//		Element root = tdoc.getRootElement();
//		List<Element> sqlElements = root.elements("sql");
//		for (Element e : sqlElements) {
//			String key = e.attributeValue("id");
//			if("true".equalsIgnoreCase(e.attributeValue("isList"))){
//				key=key+"%isList";
//			}
//			String text = e.getText();
//			String sql = formatSQL(text);
//			logger.info("cacheSQL2Map sql into cache:"+":"+key+":"+ sql);
//			sqlMap.put(schema + "_" + key, sql);
//
//			String isComm = e.attributeValue("isCommon");
//			if ("false".equalsIgnoreCase(isComm)) {
//				logger.info("cacheSQL2Map sql into cache:(isCommon=false)"+":"+ key);
//			} else {
//				sqlCommon.put(key, sql);
//				logger.info("cacheSQL2Map sql into cache:"+":"+key+":"+ sql);
//			}
//		}
//		//TAVA_Common_TAVA_Common
//		String key=schema+"_Common"+"_"+schema+"_Common";
//		commonSqlMap.put(key, sqlCommon);
//	}
//
//
//
//	/**
//	 * Read Document from a XML file.
//	 *
//	 * @param file
//	 * @return XML document object
//	 * @throws MalformedURLException
//	 * @throws DocumentException
//	 */
//	private Document read(File file) throws MalformedURLException, DocumentException {
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(file);
//		return document;
//	}
//
//	private void cacheTsqls(String schema, Document tdoc, String sqlFolder) {
//		Element root = tdoc.getRootElement();
//		List<Element> screenElements = root.elements("screen");
//		for (Element e : screenElements) {
//			String screenID = e.attributeValue("id");
//			List<Element> templateNameElements=e.elements("template");
//			if(templateNameElements!=null){
//				for(Element templateElement : templateNameElements){
//					String tempId=templateElement.attributeValue("id");
//					Map sqlMap1= getTemplateSQL(schema,templateElement,sqlFolder);
//					sqlCache.put(screenID+"_"+tempId, sqlMap1);
//				}
//			}else{
//				logger.info("No template found under screenID {}", screenID);
//			}
//		}
//	}
//
//	private Map<String,String> getTemplateSQL(String schema, Element templateElement, String sqlFolder) {
//		Map<String,String> sqlMap=new HashMap<String, String>();
//		String tempId=templateElement.attributeValue("id");
//		List<Element> sqlElements = templateElement.elements("sql");
//		if(sqlElements!=null){
//			for (Element e : sqlElements) {
//				String key = e.attributeValue("id");
//				String isList = e.attributeValue("isList");
//				if(isList!=null){
//					key = key+"%isList";
//				}
//				String text = e.getText();
//				if(text==null||text.trim().equals("")){
//					text=getSqlFromExtXML(schema,key);
//				}
//				text=formatSQL(text);
//
//				if(text!=null&&!StringUtils.isEmpty(text)){
//					logger.info("Adding SQL:"+schema+"->{} : {}", key, text);
//					sqlMap.put(key, text);
//				}
//			}
//		}else{
//			logger.info("No sql found under tempId {}", tempId);
//		}
//		return sqlMap;
//	}
//
//
//
//	private String getSqlFromExtXML(String schema, String key) {
//		return sqlMap.get(schema+"_"+key);
//	}
//
//
//
//	private String formatSQL(String sql) {
//		sql = StringUtils.replace(sql, "\n", " ");
//		sql = StringUtils.remove(sql, "\t");
//		sql = StringUtils.trimToEmpty(sql);
//		sql = StringUtils.replace(sql, "=", " = ");
//		return sql;
//	}
//
//	public Map<String,String> getSQLMap( String screenID,String templateID){
//		Map<String, String> map = new HashMap<String, String>();
//		Map<String, String> commonMap = sqlCache.get("common_"+templateID);
//		if(commonMap!=null){
//			map.putAll(commonMap);
//		}
//		String commonKey=screenID+"_"+templateID;//"TAVA_Common_TAVA_Common";
//
//		Map<String, String> screenMap=sqlCache.get(screenID+"_"+templateID);
//		if(screenMap!=null){
//			map.putAll(screenMap);
//		}else{
//			screenMap=getCommonSqlFromExtXml(commonKey);
//			map.putAll(screenMap);
//		}
//
//		return map;
//	}
//
//
//	private Map<String, String> getCommonSqlFromExtXml(String commonKey) {
//		Map<String, String> map = commonSqlMap.get(commonKey);
//		if(map==null){
//			return new HashMap<String, String>();
//		}else{
//			return map;
//		}
//	}
//
//	public Map<Integer, Map<String, Object>> getSqlCommonParams() {
//		return SqlCommonParams;
//	}
//
//	public void setSqlCommonParams(Map<Integer, Map<String, Object>> sqlCommonParams) {
//		SqlCommonParams = sqlCommonParams;
//	}
//
//	public static final void main(String[] args) throws Exception {
//		String sqltFolder = "E:/";
//		MailMergeTemplateSqlEngine engine = MailMergeTemplateSqlEngine.getInstance(sqltFolder);
//		engine = MailMergeTemplateSqlEngine.getInstance(sqltFolder);
//		engine = MailMergeTemplateSqlEngine.getInstance(sqltFolder);
//		engine = MailMergeTemplateSqlEngine.getInstance(sqltFolder);
//		Set<String> keyset = engine.sqlCache.keySet();
//		for(String key:keyset){
//			System.out.println("Key:"+key);
//			Map<String, String> sqlmap1 = engine.sqlCache.get(key);
//			Set<String> keys = sqlmap1.keySet();
//			for(String key2:keys){
//				System.out.println("SQL ID:"+key2);
//				String sql=sqlmap1.get(key2);
//				System.out.println("SQL:"+sql);
//			}
//			System.out.println("==============================");
//		}
//	}
}
