package sop.filegen.generator.impl;

import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:58
 * @Package: sop.filegen.generator.impl
 */

public class MailMergeEnginelServiceImpl implements MailMergeEnginelService {

    @Override
    public String toMailMerge(String screenID, String templateID,
                              GenRequest request, GenResult response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toMailMerge(String screenID, String templateID,
                              GenRequest request, GenResult response, Object object,
                              FieldsMetadata metadata) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initCommonInfoForGenDOC(Integer caseKey, String screenID,
                                        String templateID, GenRequest request) {
        // TODO Auto-generated method stub
    }
//
//
//	@Autowired
//	private DataSource dataSource;
//
//	private String outputPath;
//
//	public void setOutputPath(String outputPath) {
//		this.outputPath = outputPath;
//	}
//
//	protected Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//
//	@Override
//	public void initCommonInfoForGenDOC(Integer caseKey,String screenID, String templateID,GenRequest request){
//		logger.info("initCommonInfoForGenDOC, caseKey="+caseKey+", screenID="+screenID+", templateID="+templateID);
//		Map<Integer,Map<String,Object>> sqlCommonInfo =  new HashMap<Integer,Map<String,Object>>();
//		Map<String, Object> keyAndValue = getBaseInformationFromSession(request);
//		Map<String, String> criteria = getBaseCriteria(keyAndValue);
//		Map<String, String> sqlMap = MailMergeTemplateSqlEngine.getInstance().getSQLMap(screenID, templateID);
//		Map<String, Object> keyAndValueSQL = getInformationFromSQLMap(sqlMap, criteria);
//
//		sqlCommonInfo.put(caseKey, keyAndValueSQL);
//		MailMergeTemplateSqlEngine.getInstance().setSqlCommonParams(sqlCommonInfo);
//	}
//
//	@Override
//	public String toMailMerge(String screenID, String templateID, GenRequest request, GenResult response) {
//		Map<String, Object> keyAndValue = getKeyAndValue(screenID, templateID, request);
//		String templateName = getTemplateName(templateID);
//		FieldsMetadata metadata = getFieldsMetadata(keyAndValue);
//		String filePath = "";
//		if("SCCMM0010".equals(screenID)||"SC-CMM-0020".equals(screenID)){
//			filePath= generateDocx2(templateName, keyAndValue, metadata, request,screenID);
//		}else{
//			filePath= generateDocx(templateName, keyAndValue, metadata, request);
//		}
//		return filePath;
//	}
//
//
//	@Override
//	public String toMailMerge(String screenID, String templateID, GenRequest request, GenResult response, Object object, FieldsMetadata metadata) {
//		Map<String, Object> keyAndValue = getKeyAndValue(screenID, templateID, request);
//
//		FieldsMetadata metadata1 = getFieldsMetadata(keyAndValue);
//		try {
//			if (object != null) {
//				keyAndValue.putAll(MailMergeUtil.ObjectToMap(object));
//				if (metadata != null) {
//					metadata1 = MailMergeUtil.mergeFieldsMetadata(metadata, metadata1);
//				}
//				//append for remote call.
//				if (request.getFieldMeta()!=null){
//					for(String fieldName: request.getFieldMeta()){
//						metadata1.addFieldAsList(fieldName);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String templateName = getTemplateName(templateID);
//		String filePath = generateDocx(templateName, keyAndValue, metadata1, request);
//		return filePath;
//	}
//
//	private Map<String, Object> getKeyAndValue(String screenID, String templateID, GenRequest request) {
//
//		Map<String, String> sqlMap = MailMergeTemplateSqlEngine.getInstance().getSQLMap(screenID, templateID);
//		Map<String, Object> keyAndValue = getBaseInformationFromSession(request);
//		Map<String, String> criteria = getBaseCriteria(keyAndValue);
//		//get the caseKey from GenReq
//		CaseKeyAndCustKey caseKeyAndCustKey = (CaseKeyAndCustKey)request.getParameter("caseKeyAndCustKey");
//		Integer caseKey = 1;
//		if(caseKeyAndCustKey!=null){
//			caseKey = caseKeyAndCustKey.getCaseKey();
//		}
//		Map<Integer, Map<String, Object>> sqlValues = MailMergeTemplateSqlEngine.getInstance().getSqlCommonParams();
//		if(sqlValues!=null){
//			if(sqlValues.get(caseKey)!=null){
//				keyAndValue.putAll(sqlValues.get(caseKey));
//			}
//		}
//		Map<String, Object> keyAndValueSQL = getInformationFromSQLMap(sqlMap, criteria);
//		keyAndValue.putAll(keyAndValueSQL);
//
//		//add GenRequest Param
//		keyAndValue.putAll(request.getParams());
//
//		return keyAndValue;
//	}
//
//	public FieldsMetadata getFieldsMetadata(Map<String, Object> keyAndValue) {
//		FieldsMetadata metadata = new FieldsMetadata();
//		Set<String> keys = keyAndValue.keySet();
//		for (String key : keys) {
//
//			Object obj = keyAndValue.get(key);
//			if(obj==null){
//				continue;
//			}
//			if (obj instanceof List) {
//				List<Map<String, Object>> list = (List) obj;
//				if (list != null && list.size() > 0) {
//					Map<String, Object> map = list.get(0);
//					Set<String> names = map.keySet();
//					for (String name : names) {
//						metadata.addFieldAsList(key + "." + name);
//						logger.debug("metadata:" + key + "." + name);
//					}
//				}
//			}
//		}
//
//		return metadata;
//	}
//
//	private String getTemplateName(String templateID) {
//		if (templateID == null) {
//			return "";
//		}
//		// just for Demo
//		if ("template1".equals(templateID)) {
//			return "mailMergeTemplate";
//		}
//
//		return templateID;
//	}
//
//	private Map<String, Object> getInformationFromSQLMap(Map<String, String> sqlMap, Map criteria) {
//		Map<String, Object> keyAndValue = new HashMap<String, Object>();
//		if (sqlMap != null) {
//			Set<String> nameSpaceSet = sqlMap.keySet();
//			for(String nameSpace:nameSpaceSet){
//				String sql = sqlMap.get(nameSpace);
//				String[] value = nameSpace.split("%");
//				nameSpace = value[0];
//				String isList = "";
//				if(value.length==2) isList = value[1];
//				List<Map<String, Object>> keyAndValueList = getInformationFromSQLForList(sql, criteria);
//				if (keyAndValueList != null && keyAndValueList.size() == 1) {
//					//keyAndValue.putAll(keyAndValueList.get(0));
//					if(isList.equalsIgnoreCase("isList")){
//						keyAndValue.put(nameSpace, keyAndValueList);
//					}else{
//						putMapToKeyAndValue(keyAndValue,keyAndValueList.get(0));
//					}
//
//				}else if(keyAndValueList != null && keyAndValueList.size() > 0){
//					keyAndValue.put(nameSpace, keyAndValueList);
//				}
//			}
//			System.out.println(nameSpaceSet+"     "+keyAndValue);
//		}
//		return keyAndValue;
//	}
//
//
//	private List<Map<String, Object>> getInformationFromSQLForList(String sql, Object criteria) {
//		logger.debug("getInformationFromSQLForList SQL["+sql+"]");
//		final String sqlStr=sql;
//		final List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
//		final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//		final Map<String, Object> criteriaAsMap = com.pccw.oframework.dao.sqlt.JpaSqltRepositoryImpl.buildCriteriaMap(sql, criteria);
//		try{
//			final String sqlWithUR = appendWithUR(sql);
//			logger.info("executing SQL: [{}]", sql);
//			final int maxRowLimit = 1000;
//
//			jdbcTemplate.query(sqlWithUR, criteriaAsMap, new RowCallbackHandler() {
//				public void processRow(ResultSet rs) throws SQLException {
//					ResultSetMetaData meta = rs.getMetaData();
//					Map<String, Object> keyValueMap = new HashMap<String, Object>();
//					for (int i = 1; i <= meta.getColumnCount(); i++) {
//						String key = getSqlNameByMetaData(sqlStr,meta.getColumnName(i));
//						Object value = rs.getObject(i);
//						if(value==null||"".equals(value.toString().trim())){
//							value="";
//						}
//						if("11/11/1111".equals(value.toString())){
//							value="";
//						}
//						keyValueMap.put(key, value);
//					}
//					int row = rs.getRow();
//					logger.debug("row +"+row+" ["+keyValueMap+"]");
//
//					infoList.add(keyValueMap);
//					if(row>maxRowLimit){
//						logger.error("Returned resultset is larger than {}.", maxRowLimit);
//					}
//				}
//			});
//		}catch (Exception e){
//			logger.error("Unknown error in getInformationFromSQLForList", e);
//		}
//		return infoList;
//	}
//
//	private String getSqlNameByMetaData(String sql,String key) {
//		if(sql==null||key==null){
//			return key;
//		}
//		String keyUpper=key.toUpperCase();
//		sql=sql.replaceAll(",", " , ");
//		String sqlUpper=sql.toUpperCase();
//
//		int begin=sqlUpper.indexOf(" "+keyUpper+" ")+1;
//		int end=begin+keyUpper.length();
//		String name=sql.substring(begin, end);
//		if(keyUpper.equals(name.toUpperCase())){
//			return name;
//		}else{
//			return key;
//		}
//	}
//
//
//
//	private Map<String, String> getBaseCriteria(Map<String, Object> keyAndValue) {
//		Map<String, String> criteria = new HashMap<String, String>();
//		Set<String> keys = keyAndValue.keySet();
//		for(String key:keys){
//			if(keyAndValue.get(key)==null){
//				continue;
//			}
//			Object param = keyAndValue.get(key);
//			if(param instanceof String||
//					param instanceof Integer||
//					param instanceof Character||
//					param instanceof Double||
//					param instanceof Float ||
//					param instanceof Boolean||
//					param instanceof Long||
//					param instanceof Byte){
//				criteria.put(key, param.toString());
//			}
//		}
//		return criteria;
//	}
//
//	private Map<String, Object> getBaseInformationFromSession(GenRequest request) {
//		Map<String, Object> keyAndValue = new HashMap<String, Object>();
//		CaseKeyAndCustKey caseKeyAndCustKey = (CaseKeyAndCustKey) request.getParameter(WebConstants.SESSION_KEY_CASEKEY_AND_CUSTKEY);
//
//		if (caseKeyAndCustKey == null) {
//			caseKeyAndCustKey=new CaseKeyAndCustKey();
//		}
//		putValueToKeyAndValue(keyAndValue,"caseKey", caseKeyAndCustKey.getCaseKey());
//		putValueToKeyAndValue(keyAndValue,"custKey", caseKeyAndCustKey.getCustKey());
//
//		CaseInformationHeader caseInfoHeader = (CaseInformationHeader) request.getParameter(WebConstants.SESSION_KEY_CASEINFOR_HEADER);
//		if (caseInfoHeader == null) {
//			caseInfoHeader=new CaseInformationHeader();
//		}
//		if(caseInfoHeader.getCaseNumObj()!=null){
//			putValueToKeyAndValue(keyAndValue,"CaseFileNum", caseInfoHeader.getCaseNumObj().getDisplayString());
//		}
//
//		putValueToKeyAndValue(keyAndValue,"GrantType", caseInfoHeader.getGrantType());
//		putValueToKeyAndValue(keyAndValue,"ProcessingFlowStage", caseInfoHeader.getProcessingFlowStage());
//
//		CustomerHeader custHerder = (CustomerHeader) request.getParameter(WebConstants.SESSION_KEY_CUSTMER_HEADER);
//
//		if (custHerder == null) {
//			custHerder=new CustomerHeader();
//		}
//		putValueToKeyAndValue(keyAndValue,"EngFulName", custHerder.getEngFulName());
//		putValueToKeyAndValue(keyAndValue,"ChnFullName", custHerder.getChnFullName());
//		putValueToKeyAndValue(keyAndValue,"IdNumber", custHerder.getIdNumber());
//		putValueToKeyAndValue(keyAndValue,"IdType1", custHerder.getIdType());
//		putValueToKeyAndValue(keyAndValue,"IdTypeDesc", custHerder.getIdTypeDesc());
//		putValueToKeyAndValue(keyAndValue,"Sex", custHerder.getSex());
//
//		putValueToKeyAndValue(keyAndValue,"VicEName", custHerder.getEngFulName());
//		putValueToKeyAndValue(keyAndValue,"VicCName", custHerder.getChnFullName());
//		putValueToKeyAndValue(keyAndValue,"VicIDNum", custHerder.getIdNumber());
//		putValueToKeyAndValue(keyAndValue,"VicIDType", custHerder.getIdType());
//		putValueToKeyAndValue(keyAndValue,"VicIDTypeDesc", custHerder.getIdTypeDesc());
//		putValueToKeyAndValue(keyAndValue,"VicSex", custHerder.getSex());
//
//		putValueToKeyAndValue(keyAndValue,"ApplEName", custHerder.getEngFulName());
//		putValueToKeyAndValue(keyAndValue,"ApplCName", custHerder.getChnFullName());
//		putValueToKeyAndValue(keyAndValue,"ApplIDNum", custHerder.getIdNumber());
//		putValueToKeyAndValue(keyAndValue,"ApplIDType", custHerder.getIdType());
//		putValueToKeyAndValue(keyAndValue,"ApplIDTypeDesc", custHerder.getIdTypeDesc());
//		putValueToKeyAndValue(keyAndValue,"ApplSex", custHerder.getSex());
//
//		PrintFaceSheetDTO printFaceSheetDTO=(PrintFaceSheetDTO)request.getParameter(WebConstants.SESSION_KEY_PRINTFACESHEET_DTO);
//		if(printFaceSheetDTO !=null){
//			putValueToKeyAndValue(keyAndValue,"CaseFPNBarCode", printFaceSheetDTO.getCaseFPNBarCode());
//			putValueToKeyAndValue(keyAndValue,"FolderPartNum", printFaceSheetDTO.getFolderPartNum());
//		}else{
//			putValueToKeyAndValue(keyAndValue,"CaseFPNBarCode", "");
//			putValueToKeyAndValue(keyAndValue,"FolderPartNum", "");
//		}
//
//		SsaPrintDeclarationDTO ssaPrintDTO = (SsaPrintDeclarationDTO)request.getParameter(WebConstants.SESSION_KEY_PRINTDECLARATION_DTO);
//		if(ssaPrintDTO != null){
//			putValueToKeyAndValue(keyAndValue,"LastRevDate",ssaPrintDTO.getLastRevDate());
//		}else{
//			putValueToKeyAndValue(keyAndValue,"LastRevDate","");
//		}
//
//		CSSS2UserDetails authenticatedUser=(CSSS2UserDetails) request.getParameter(WebConstants.SESSION_KEY_AUTHENTICATEED_USER);
//		if (authenticatedUser != null) {
//			putValueToKeyAndValue(keyAndValue,"CurOffrEName", authenticatedUser.getEngFullName().replaceAll("  ",""));
//			putValueToKeyAndValue(keyAndValue,"CurOffrCName", authenticatedUser.getChnFullName().replaceAll("  ",""));
//			putValueToKeyAndValue(keyAndValue,"logProcUnit", authenticatedUser.getLogonProcUnit());
//			if(authenticatedUser.getPrimaryProcUnit() !=null){
//				putValueToKeyAndValue(keyAndValue,"primaryProcUnit", authenticatedUser.getPrimaryProcUnit());
//			}
//			putValueToKeyAndValue(keyAndValue,"logPostId", authenticatedUser.getPostID());
//			putValueToKeyAndValue(keyAndValue,"logUId", authenticatedUser.getUserID());
//		}else{
//			putValueToKeyAndValue(keyAndValue,"CurOffrEName", "");
//			putValueToKeyAndValue(keyAndValue,"CurOffrCName", "");
//		}
//
//
//		return keyAndValue;
//	}
//
//
//
//	private String generateDocx(String templateName, Map<String, Object> keyAndValue, FieldsMetadata metadata, GenRequest request) {
//		try {
//			// Create IXDocReport
//			final String reportFileName = FileGeneratorUtils.genReportFileNameForMailMerge(request);
//
//			final String today = DateUtils.formatDateTime(DateFormatConstant.DATE_WITHOUT_SEPARATOR_SHORT, Sys.getServerDate());
//			String outputFolder = outputPath+"/docx/"+ today;
//
//			String outFile = FileGeneratorUtils.getReportOutputFullPath(outputFolder, reportFileName, ".docx");
//
//			InputStream in = new FileInputStream(getDocxTemplate(templateName + ".docx"));
//			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
//
//			report.setFieldsMetadata(metadata);
//			boolean HaveEmpoyment=false;
//			if(keyAndValue.get("empVic3") !=null){
//				keyAndValue.put("HaveEmpoyment", HaveEmpoyment);
//			}else{
//				HaveEmpoyment=true;
//				keyAndValue.put("HaveEmpoyment", HaveEmpoyment);
//			}
//
//			CaseInformationHeader caseInformationHeader = (CaseInformationHeader)request.getParameter("caseInforHeader");
//			String schameType = "";
//			if(caseInformationHeader != null && caseInformationHeader.getCaseNumObj().getMid() != null){
//				if(caseInformationHeader.getCaseNumObj().getMid()!=""){
//					schameType = caseInformationHeader.getCaseNumObj().getMid().trim();
//					if(schameType.equals("S")){
//						getAbsenceRecord(keyAndValue);
//					}
//				}
//			}
//
//
//			IContext context = report.createContext();
//			Set<String> keySet = keyAndValue.keySet();
//			Iterator<String> it = keySet.iterator();
//			while (it.hasNext()) {
//				String key = it.next();
//				Object value = keyAndValue.get(key);
//				if (value == null) {
//					value = "";
//				}
//				logger.debug(key+":"+value);
//				context.put(key, value);
//			}
//
//
//			OutputStream out = new FileOutputStream(new File(outFile));
//			report.process(context, out);
//			return outFile;
//		} catch (IOException e) {
//			logger.error("failed to download file.", e);
//		} catch (XDocReportException e) {
//			logger.error("failed to generate docx.", e);
//		}
//		return null;
//	}
//
//
//
//
//	private String generateDocx2(String templateName, Map<String, Object> keyAndValue, FieldsMetadata metadata, GenRequest request,String screenID) {
//		try {
//			// For Create Correspondence
//			final String reportFileName = FileGeneratorUtils.genReportFileNameForMailMerge(request);
//
//			final String today = DateUtils.formatDateTime(DateFormatConstant.DATE_WITHOUT_SEPARATOR_SHORT, Sys.getServerDate());
//
//
//			String enOrzh = "en";
//			if(request.getParameter("enOrzh")!=null){
//				enOrzh = (String) request.getParameter("enOrzh");
//			}
//
//			String outputFolder = outputPath+"/docx/"+enOrzh+"/"+ today;
//
//			String outFile = FileGeneratorUtils.getReportOutputFullPath(outputFolder, reportFileName, ".docx");
//
//			//InputStream in = new FileInputStream(getDocxTemplate(templateName + ".docx"));
//
//			String path=FileUtil.mergeSubDirectory(new String[]{System.getProperty("rrr.config.path"),"environment", System.getProperty("csss2.environment")});
//			//InputStream is = this.getClass().getClassLoader().getResourceAsStream(path+"fileupload.properties");
//			InputStream is;
//			String storePath="";
//			try {
//				is = new FileInputStream(new File(path+"fileupload.properties"));
//				Properties pro = new Properties();
//				pro.load(is);
//				String folder = "en";
//				if("C".equals(templateName.substring(templateName.length()-1))){
//					folder = "zh";
//				}
//				storePath = pro.getProperty("com.pccw.oframework.sys.upload.persistenceFolder") + File.separator + folder + File.separator;
//				} catch (FileNotFoundException e) {
//
//					e.printStackTrace();
//				} catch (IOException e) {
//
//					e.printStackTrace();
//				}
//				templateName= templateName.substring(0, templateName.length()-1);
//				File f = new File(storePath+templateName + ".docx");
//				if(!f.exists()){
//					//throw new FileNotFoundException("Template file "+filePath+" not found!");
//					logger.debug("file not found");
//
//				}
//				logger.debug("currentfilePath............."+storePath+templateName + ".docx");
//				InputStream in = new FileInputStream(storePath+templateName + ".docx");
//
//				IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
//
//				report.setFieldsMetadata(metadata);
//				IContext context = report.createContext();
//				Set<String> keySet = keyAndValue.keySet();
//				Iterator<String> it = keySet.iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					Object value = keyAndValue.get(key);
//
//					if("SC-CMM-0020".equals(screenID)){
//						value = "";
//					}else{
//						if (value == null) {
//							value = "";
//						}
//					}
//
//					logger.debug(key+":"+value);
//					context.put(key, value);
//				}
//
//				OutputStream out = new FileOutputStream(new File(outFile));
//				report.process(context, out);
//				return outFile;
//			}catch (IOException e) {
//				logger.error("failed to download file.", e);
//			}catch (XDocReportException e) {
//				logger.error("failed to generate docx.", e);
//			}
//			return null;
//	}
//
//	private String getDocxTemplate(String templateName) throws FileNotFoundException {
//
//		String filePath = FileGeneratorUtils.getMailMergeTemplatePath() + templateName;
//		File f = new File(filePath);
//		if(!f.exists()){
//			throw new FileNotFoundException("Template file "+filePath+" not found!");
//		}
//
//		return filePath;
//	}
//
//	private void putValueToKeyAndValue(Map<String, Object> keyAndValue,
//			String key, Object obj) {
//		if(obj==null){
//			keyAndValue.put(key, "");
//		}else{
//			keyAndValue.put(key, obj.toString());
//		}
//	}
//
//	private void putMapToKeyAndValue(Map<String, Object> keyAndValue,
//			Map<String, Object> newKeyAndValue) {
//		if(newKeyAndValue==null||keyAndValue==null){
//			return;
//		}
//		Set<String> set = newKeyAndValue.keySet();
//		for(String key:set){
//			Object value = keyAndValue.get(key);
//			if(value==null||"".equals(value.toString().trim())||"N/A".equals(value.toString().trim())){
//				keyAndValue.put(key, newKeyAndValue.get(key));
//			}
//		}
//	}
//
//	private String appendWithUR(String sql){
//		String compstr = sql.toLowerCase();
//		if (!compstr.matches(".*with ur.*")) {
//			sql += " with ur for read only ";
//
//			if (logger.isTraceEnabled()) {
//				logger.trace("Appending \"WITH UR FOR READ ONLY\" to query.");
//			}
//		}
//		return sql;
//	}
//
//	public String getDate(String str1, String str2){
//		String result = null;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		Date d1 = null;
//		Date d2 = null;
//		try {
//			d1 = dateFormat.parse(str1);
//			d2 = dateFormat.parse(str2);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		if (d1 != null && d2 != null) {
//			long mid = (d2.getTime() - d1.getTime()) / 86400000;
//			result = String.valueOf(mid);
//		}
//		return result;
//	}
//
//	private Date getPreYearDate(Date date){
//		Calendar cal=Calendar.getInstance();
//		cal.setTimeInMillis(date.getTime());
//		cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1 );
//		Date befApplDate = cal.getTime();
//		return befApplDate;
//	}
//
//	private void getAbsenceRecord(Map<String, Object> keyAndValue){
//		String haveAbsenceRecord = keyAndValue.get("HaveAbsenceRecord").toString();
//		if(haveAbsenceRecord != null){
//			if("True".equals(haveAbsenceRecord)){
//				String lastRevDate = keyAndValue.get("LastRevDate").toString();
//				String dateOfAppl = keyAndValue.get("DateOfAppl").toString();
//				List<Map<String,Object>> absenceRecordList = (List<Map<String, Object>>)keyAndValue.get("AbsenceRecord");
//				boolean haveDep = false;
//				boolean haveArr = false;
//				int absenceRecordCount = 0;
//				int absTotal = 0;
//				int listSize = absenceRecordList.size();
//				Map<String,Object> absenceRecord = new HashMap<String,Object>();
//
//				for(int i=0;i<listSize;i++){
//					Map<String, Object> map = absenceRecordList.get(0);
//					try {
//						if( (lastRevDate == "" && !DateUtils.parse("dd/MM/yyyy", map.get("DepDate").toString()).before(getPreYearDate(DateUtils.parse("dd/MM/yyyy", dateOfAppl))))
//								|| (lastRevDate != "" && !DateUtils.parse("dd/MM/yyyy", map.get("DepDate").toString()).before(DateUtils.parse("dd/MM/yyyy", lastRevDate)))){
//							if("D".equals(map.get("ClrType")) && "N".equals(map.get("CanInd"))){
//								if(!haveDep){
//									absenceRecord.put("RADepDateTime", map.get("DepDate"));
//									absenceRecordCount++;
//									haveDep = true;
//								}else if(haveDep && haveArr){
//									absenceRecord.put("RANetDays",getDate(absenceRecord.get("RADepDateTime").toString(),absenceRecord.get("RAArrDateTime").toString()));
//									absTotal += Integer.parseInt(absenceRecord.get("RANetDays").toString());
//									absenceRecordList.add(absenceRecord);
//									haveArr = false;
//									absenceRecord = new HashMap<String,Object>();
//									absenceRecord.put("RADepDateTime", map.get("DepDate"));
//									absenceRecordCount++;
//								}
//							}
//							if("A".equals(map.get("ClrType")) && "N".equals(map.get("CanInd"))){
//								if(haveDep){
//									absenceRecord.put("RAArrDateTime", map.get("DepDate"));
//									haveArr = true;
//								}
//							}
//						}
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//
//					absenceRecordList.remove(0);
//				}
//				if(haveDep){
//					if(!haveArr){
//						absenceRecord.put("RAArrDateTime", "");
//						absenceRecord.put("RANetDays",getDate(absenceRecord.get("RADepDateTime").toString(),DateUtils.parseDate(DateUtils.getSystemDate(),"dd/MM/yyyy")));
//						absTotal += Integer.parseInt(absenceRecord.get("RANetDays").toString());
//					}else{
//						absenceRecord.put("RANetDays",getDate(absenceRecord.get("RADepDateTime").toString(),absenceRecord.get("RAArrDateTime").toString()));
//						absTotal += Integer.parseInt(absenceRecord.get("RANetDays").toString());
//					}
//					absenceRecordList.add(absenceRecord);
//					keyAndValue.put("AbsTotal", String.valueOf(absTotal));
//					keyAndValue.put("absenceRecordCount", absenceRecordCount);
//				}
//				if(absenceRecordCount==0){
//					keyAndValue.put("HaveAbsenceRecord", "False");
//					keyAndValue.put("NoRecordofAbsMsg", "沒有離港記錄 No absence records");
//					keyAndValue.put("hInvTral", "");
//				}
//			}
//		}
//	}
}
