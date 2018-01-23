package com.gw.das.web.controller.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.common.easyui.ComboboxBean;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AdvisoryEnum;
import com.gw.das.common.enums.AreaEnum;
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.BrowserEnum;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.GenderEnum;
import com.gw.das.common.enums.PlanTimeTypeEnum;
import com.gw.das.common.enums.ResultCodeEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SilenceEnum;
import com.gw.das.common.response.ResultCode;
import com.gw.das.dao.market.entity.DasUserScreenEntity;
import com.gw.das.dao.market.entity.TagEntity;
import com.gw.das.service.market.ChannelService;
import com.gw.das.service.market.TagService;
import com.gw.das.service.website.DasUserScreenService;
import com.gw.das.web.controller.system.BaseController;

@Controller
@RequestMapping("/DasUserScreenController")
public class DasUserScreenController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DasUserScreenController.class);

	@Autowired
	private DasUserScreenService dasUserScreenService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ChannelService channelService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("areaEnum", AreaEnum.getList());
			request.setAttribute("browserEnum", BrowserEnum.getList());
			request.setAttribute("clientEnum", ClientEnum.getList());
			request.setAttribute("genderEnum", GenderEnum.getList());
			request.setAttribute("advisoryEnum", AdvisoryEnum.getList());
			request.setAttribute("behaviorTypeEnum", BehaviorTypeEnum.getList("dasUserScreen"));
			request.setAttribute("planTimeTypeEnum", PlanTimeTypeEnum.getList());
			request.setAttribute("silenceEnum", SilenceEnum.getList(BehaviorTypeEnum.demo.getLabelKey()));
			request.setAttribute("sendStatusEnum", SendStatusEnum.getList());

			List<TagEntity> tagList = tagService.findList(new TagEntity());
			request.setAttribute("tagList", tagList);

			List<String> channelList = channelService.findListGroupName(ChannelTypeEnum.webSite);
			request.setAttribute("channelList", channelList);

			return "/market/dasUserScreen/dasUserScreen";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DasUserScreenEntity> pageList(HttpServletRequest request,
			@ModelAttribute DasUserScreenEntity dasUserScreenEntity) {
		try {
			PageGrid<DasUserScreenEntity> pageGrid = dasUserScreenService
					.findPageList(super.createPageGrid(request, dasUserScreenEntity));
			for(Object obj: pageGrid.getRows()){
				DasUserScreenEntity record = (DasUserScreenEntity)obj;
				record.setPlanTimeType(PlanTimeTypeEnum.format(record.getPlanTimeType()));
				record.setBehaviorType(BehaviorTypeEnum.format(record.getBehaviorType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DasUserScreenEntity>();
		}
	}

	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public DasUserScreenEntity findById(Long screenId) {
		try {
			return dasUserScreenService.findById(screenId);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new DasUserScreenEntity();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute DasUserScreenEntity dasUserScreenEntity) {
		try {
			// 统计数量
			Map<String, String> telMap = new HashMap<String, String>();
			Map<String, String> emailMap = new HashMap<String, String>();
			dasUserScreenService.getTelAndEmailList(dasUserScreenEntity, telMap, emailMap);
			dasUserScreenEntity.setResultSmsCount(Long.parseLong(telMap.size() + ""));
			dasUserScreenEntity.setResultEmailCount(Long.parseLong(emailMap.size() + ""));
			// 新增操作
			dasUserScreenService.saveOrUpdate(dasUserScreenEntity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(String screenIdArray) {
		try {
			dasUserScreenService.deleteByIdArray(screenIdArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 统计
	 */
	@RequestMapping(value = "/resultCount", method = { RequestMethod.POST })
	@ResponseBody
	public DasUserScreenEntity resultCount(HttpServletRequest request, DasUserScreenEntity dasUserScreenEntity) {
		try {
			// 统计数量
			Map<String, String> telMap = new HashMap<String, String>();
			Map<String, String> emailMap = new HashMap<String, String>();
			dasUserScreenService.getTelAndEmailList(dasUserScreenEntity, telMap, emailMap);
			dasUserScreenEntity.setResultSmsCount(Long.parseLong(telMap.size() + ""));
			dasUserScreenEntity.setResultEmailCount(Long.parseLong(emailMap.size() + ""));
			// 3.返回
			return dasUserScreenEntity;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * change事件，获取下拉列表
	 */
	@RequestMapping(value = "/getSilenceList", method = { RequestMethod.POST })
	@ResponseBody
	public List<ComboboxBean> getSilenceList(HttpServletRequest request) {
		try {
			List<ComboboxBean> comboboxBeanList = new ArrayList<ComboboxBean>();
			ComboboxBean comboboxBean = new ComboboxBean();
			comboboxBean.setId("0");
			comboboxBean.setText("--请选择--");
			comboboxBean.setValue("");
			comboboxBeanList.add(comboboxBean);
			
			String type = request.getParameter("type");
			List<SilenceEnum> list = SilenceEnum.getList(type);
			if(null != list && list.size()>0){
				for(int i=0; i<list.size(); i++){
					SilenceEnum silenceEnum = list.get(i);
					ComboboxBean bean = new ComboboxBean();
					bean.setId(i+"");
					bean.setText(silenceEnum.getValue());
					bean.setValue(silenceEnum.getLabelKey());
					comboboxBeanList.add(bean);
				}
			}
			
			return comboboxBeanList;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return null;
		}
	}
	
}
