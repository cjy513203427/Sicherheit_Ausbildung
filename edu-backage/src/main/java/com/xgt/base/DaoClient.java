package com.xgt.base;

import com.alibaba.fastjson.JSONObject;
import com.xgt.util.FreeMakerParser;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

/**
 *  数据持久层操作 封装
 *  @author liu ao  
 *  @created 2018年3月19日 下午5:40:57
 */
public class DaoClient {

	private static final Logger logger = LoggerFactory.getLogger(DaoClient.class);
	private NamedParameterJdbcTemplate namedJdbcTemplate ;

	private static final String INTEGER = "java.lang.Integer";
	private static final String LONG = "java.lang.Long";
	private static final String BIGDECIMAL = "java.math.BigDecimal";
	private static List<String>  TYPE_LIST = new LinkedList<String>();

	static {
		TYPE_LIST.add("java.lang.Integer");
		TYPE_LIST.add("java.lang.Long");
		TYPE_LIST.add("java.math.BigDecimal");
		TYPE_LIST.add("java.lang.String");
	}

	private XmlParse xmlParse ;

	public XmlParse getXmlParse() {
		return xmlParse;
	}

	public void setXmlParse(XmlParse xmlParse) {
		this.xmlParse = xmlParse;
	}

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	/**
	 *  查询单个对象
	 *  @param sqlId
	 *  @param param
	 *  @param clazz
	 *  @return
	 *  @author liu ao  
	 *  @created 2018年3月19日 下午5:37:47
	 */
	public <T> T queryForObject(String sqlId, Object param, Class<T> clazz){
		String clazzStr =  clazz.getName();
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		String sql = XmlParse.getSql(sqlId);
		sql = FreeMakerParser.process(sql, paramMap);

		try {
			if (TYPE_LIST.contains(clazzStr)) {
				return namedJdbcTemplate.queryForObject(sql, paramMap, clazz);
			}

			RowMapper<T> rm = BeanPropertyRowMapper.newInstance(clazz);
			return namedJdbcTemplate.queryForObject(sql, paramMap, rm);

		} catch (EmptyResultDataAccessException e){
			logger.info(".......databse no record .........");
			return  null;
		}

	}

	/**
	 *  查询单个对象
	 *  @param sqlId
	 *  @param clazz
	 *  @return
	 *  @author liu ao
	 *  @created 2018年3月19日 下午5:37:47
	 */
	public <T> T queryForObject(String sqlId, Class<T> clazz){
		return queryForObject(sqlId,new HashMap<>(), clazz);
	}


	/**
	 *  查询map 结果
	 *  @param sqlId
	 *  @param param
	 *  @return
	 *  @author liu ao
	 *  @created 2018年3月19日 下午5:37:47
	 */
	public  Map<String, Object> queryForMap(String sqlId, Object param){
		String sql = xmlParse.getSql(sqlId);
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		sql = FreeMakerParser.process(sql, paramMap);

		try {
			return  namedJdbcTemplate.queryForMap(sql, paramMap);
		} catch (EmptyResultDataAccessException e){
			logger.info(".......databse no record .........");
			return  null;
		}

	}
	
	/**
	 *  查询多个对象
	 *  @param sqlId
	 *  @param param
	 *  @param clazz
	 *  @return
	 *  @author liu ao
	 *  @created 2018年3月19日 下午5:38:13
	 */
	public <T> List<T>  queryForList(String sqlId, Object param, Class<T> clazz){
		String clazzStr =  clazz.getName();
		RowMapper<T> rm = BeanPropertyRowMapper.newInstance(clazz);
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		String sql = XmlParse.getSql(sqlId);
		sql = FreeMakerParser.process(sql, paramMap);
		try {
			if (TYPE_LIST.contains(clazzStr)) {
				return namedJdbcTemplate.queryForList(sql,paramMap ,clazz );
			}
			return  namedJdbcTemplate.query(sql, paramMap, rm);
		} catch (EmptyResultDataAccessException e){
			logger.info(".......databse no record .........");
			return  null;
		}
	}

	/**
	 *  根据参数查询出map List
	 * @author liuao
	 * @date 2018/6/7 10:50
	 */
	public List<Map<String,Object>> queryForList(String sqlId, Object param){
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		String sql = XmlParse.getSql(sqlId);
		sql = FreeMakerParser.process(sql, paramMap);
		try {
			return namedJdbcTemplate.queryForList(sql,paramMap );
		} catch (EmptyResultDataAccessException e){
			logger.info(".......databse no record .........");
			return  null;
		}
	}



	/**
	 *  查询多个对象
	 *  @param sqlId
	 *  @param clazz
	 *  @return
	 *  @author liu ao
	 *  @created 2018年3月19日 下午5:38:13
	 */
	public <T> List<T>  queryForList(String sqlId, Class<T> clazz){
		return queryForList(sqlId, new HashMap<>(), clazz);
	}
	
	/**
	 *  执行sql语句，更新or保存or 删除，返回执行成功个数
	 *  @param sqlId
	 *  @param param
	 *  @return
	 *  @author liu ao  
	 *  @created 2018年3月19日 下午5:38:39
	 */
	public int excute(String sqlId, Object param){
		String sql = xmlParse.getSql(sqlId);
		@SuppressWarnings("unchecked")
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		sql = FreeMakerParser.process(sql, paramMap);
        return namedJdbcTemplate.update(sql, paramMap);
	}
	
	/**
	 *  保存数据，并返回主键id
	 *  @param sqlId
	 *  @param param
	 *  @return
	 *  @author liu ao  
	 *  @created 2018年3月19日 下午5:39:23
	 */
	public int insertAndGetId(String sqlId, Object param){
		String sql =  xmlParse.getSql(sqlId
		);
		@SuppressWarnings("unchecked")
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		sql = FreeMakerParser.process(sql, paramMap);
		MapSqlParameterSource parameters = new MapSqlParameterSource(paramMap);  
        KeyHolder keyHolder = new GeneratedKeyHolder();    
        namedJdbcTemplate.update( sql, parameters, keyHolder, new String[] {"ID" }); 
        return keyHolder.getKey().intValue();   
	}
	
	/**
	 *  批量保存，并获得主键id
	 *  @param sqlId
	 *  @param param
	 *  @return
	 *  @author liu ao  
	 *  @created 2018年3月19日 下午5:39:59
	 */
	public  List<Integer> batchInsertAndGetId(String sqlId, Object param){
		String sql =  xmlParse.getSql(sqlId);
		@SuppressWarnings("unchecked")
		Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);
		sql = FreeMakerParser.process(sql, paramMap);
		MapSqlParameterSource parameters = new MapSqlParameterSource(paramMap);  
        KeyHolder keyHolder = new GeneratedKeyHolder();    
        namedJdbcTemplate.update( sql, parameters, keyHolder, new String[] {"ID" }); 
        
        List<Integer> ids = new ArrayList<Integer>();
        for (Map<String,Object> map :  keyHolder.getKeyList()) {
        	Integer id = MapUtils.getInteger(map, "GENERATED_KEY");
        	ids.add(id);
		}
        return ids;
	}

}
