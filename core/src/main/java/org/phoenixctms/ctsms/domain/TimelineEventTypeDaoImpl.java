// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.Collection;

import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.util.L10nUtil;
import org.phoenixctms.ctsms.util.L10nUtil.Locales;
import org.phoenixctms.ctsms.vo.TimelineEventTypeVO;

/**
 * @see TimelineEventType
 */
public class TimelineEventTypeDaoImpl
		extends TimelineEventTypeDaoBase
{

	/**
	 * @inheritDoc
	 */
	@Override
	protected Collection<TimelineEventType> handleFindByVisibleId(Boolean visible, Long typeId)
	{
		org.hibernate.Criteria typeCriteria = this.getSession().createCriteria(TimelineEventType.class);
		typeCriteria.setCacheable(true);
		CriteriaUtil.applyVisibleIdCriterion("visible", typeCriteria, visible, typeId);
		return typeCriteria.list();
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private TimelineEventType loadTimelineEventTypeFromTimelineEventTypeVO(TimelineEventTypeVO timelineEventTypeVO)
	{
		// TODO implement loadTimelineEventTypeFromTimelineEventTypeVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadTimelineEventTypeFromTimelineEventTypeVO(TimelineEventTypeVO) not yet implemented.");
		TimelineEventType timelineEventType = null;
		Long id = timelineEventTypeVO.getId();
		if (id != null) {
			timelineEventType = this.load(id);
		}
		if (timelineEventType == null)
		{
			timelineEventType = TimelineEventType.Factory.newInstance();
		}
		return timelineEventType;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TimelineEventType timelineEventTypeVOToEntity(TimelineEventTypeVO timelineEventTypeVO)
	{
		TimelineEventType entity = this.loadTimelineEventTypeFromTimelineEventTypeVO(timelineEventTypeVO);
		this.timelineEventTypeVOToEntity(timelineEventTypeVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void timelineEventTypeVOToEntity(
			TimelineEventTypeVO source,
			TimelineEventType target,
			boolean copyIfNull)
	{
		super.timelineEventTypeVOToEntity(source, target, copyIfNull);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TimelineEventTypeVO toTimelineEventTypeVO(final TimelineEventType entity)
	{
		return super.toTimelineEventTypeVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toTimelineEventTypeVO(
			TimelineEventType source,
			TimelineEventTypeVO target)
	{
		super.toTimelineEventTypeVO(source, target);
		target.setName(L10nUtil.getTimelineEventTypeName(Locales.USER, source.getNameL10nKey()));
		String titlePresetL10nKey = source.getTitlePresetL10nKey();
		if (titlePresetL10nKey != null && titlePresetL10nKey.length() > 0) {
			target.setTitlePreset(L10nUtil.getTimelineEventTitlePreset(Locales.USER, titlePresetL10nKey));
		}
	}
}