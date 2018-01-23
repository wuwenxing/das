package com.gw.das.common.easyui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 树形菜单Bean对象
 * 
 * @author wayne
 */
public class TreeBeanUtil {

	/**
	 * 树形菜单格式化
	 * 
	 * @param nodeListTmp
	 * @param fillChild
	 *            是否填充child节点数据
	 * @return
	 */
	public static List<TreeBean> formatTreeBean(List<TreeBean> nodeListTmp, boolean fillChild) {
		List<TreeBean> nodeList = new ArrayList<TreeBean>();
		for (TreeBean outNode : nodeListTmp) {
			boolean flag = false;
			for (TreeBean inNode : nodeListTmp) {
				if (outNode.getParentId() != null && outNode.getParentId().equals(inNode.getId())) {
					flag = true;
					if (fillChild && StringUtils.isBlank(inNode.getParentId())) {
						if (inNode.getChild() == null) {
							inNode.setChild(new ArrayList<TreeBean>());
						}
						inNode.getChild().add(outNode);
					} else {
						if (inNode.getChildren() == null) {
							inNode.setChildren(new ArrayList<TreeBean>());
						}
						inNode.getChildren().add(outNode);
					}
					break;
				}
			}
			if (!flag) {
				nodeList.add(outNode);
			}
		}
		return nodeList;
	}

	/**
	 * 树形菜单格式化
	 * 
	 * @param nodeListTmp
	 * @return
	 */
	public static List<TreeBean> formatTreeBean(List<TreeBean> nodeListTmp) {
		List<TreeBean> nodeList = new ArrayList<TreeBean>();
		for (TreeBean outNode : nodeListTmp) {
			boolean flag = false;
			for (TreeBean inNode : nodeListTmp) {
				if (outNode.getParentId() != null && outNode.getParentId().equals(inNode.getId())) {
					flag = true;
					if (inNode.getChildren() == null) {
						inNode.setChildren(new ArrayList<TreeBean>());
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
