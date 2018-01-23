package com.gw.das.common.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形字典Bean对象
 * 
 * @author wayne
 */
public class DictTreeBeanUtil {

	/**
	 * 树形字典格式化
	 * 
	 * @param nodeListTmp
	 * @return
	 */
	public static List<DictTreeBean> formatDictTreeBean(List<DictTreeBean> nodeListTmp) {
		List<DictTreeBean> nodeList = new ArrayList<DictTreeBean>();
		for (DictTreeBean outNode : nodeListTmp) {
			boolean flag = false;
			for (DictTreeBean inNode : nodeListTmp) {
				if (outNode.getParentDictCode() != null && outNode.getParentDictCode().equals(inNode.getDictCode())) {
					flag = true;
					if (inNode.getChildren() == null) {
						inNode.setChildren(new ArrayList<DictTreeBean>());
					}
					inNode.getChildren().add(outNode);
					break;
				}
			}
			if (!flag) {
				nodeList.add(outNode);
			}
		}
		return nodeList;
	}
}