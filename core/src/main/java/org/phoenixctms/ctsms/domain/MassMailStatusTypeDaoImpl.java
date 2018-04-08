// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// This file is part of the Phoenix CTMS project (www.phoenixctms.org),
// distributed under LGPL v2.1. Copyright (C) 2011 - 2017.
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.EntityIDComparator;
import org.phoenixctms.ctsms.util.L10nUtil;
import org.phoenixctms.ctsms.util.L10nUtil.Locales;
import org.phoenixctms.ctsms.vo.MassMailStatusTypeVO;

/**
 * @see MassMailStatusType
 */
public class MassMailStatusTypeDaoImpl
extends MassMailStatusTypeDaoBase
{

	private final static EntityIDComparator ID_COMPARATOR = new EntityIDComparator<MassMailStatusType>(false);

	private org.hibernate.Criteria createMassMailStatusTypeCriteria() {
		org.hibernate.Criteria massMailStatusTypeCriteria = this.getSession().createCriteria(MassMailStatusType.class);
		massMailStatusTypeCriteria.setCacheable(true);
		return massMailStatusTypeCriteria;
	}

	protected Collection<MassMailStatusType> handleFindInitialStates()
	{
		org.hibernate.Criteria massMailStatusTypeCriteria = createMassMailStatusTypeCriteria();
		massMailStatusTypeCriteria.add(Restrictions.eq("initial", true));
		massMailStatusTypeCriteria.addOrder(Order.asc("id"));
		return massMailStatusTypeCriteria.list();
	}

	protected Collection<MassMailStatusType> handleFindTransitions(Long statusTypeId)
	{
		MassMailStatusType statusType = this.load(statusTypeId);
		Iterator<MassMailStatusType> it = null;
		if (statusType != null) {
			it = statusType.getTransitions().iterator();
		}
		ArrayList<MassMailStatusType> result = new ArrayList<MassMailStatusType>();
		if (it != null) { // force load:
			while (it.hasNext()) {
				result.add(this.load(it.next().getId()));
			}
		}
		Collections.sort(result, ID_COMPARATOR);
		return result;
	}


	private MassMailStatusType loadMassMailStatusTypeFromMassMailStatusTypeVO(MassMailStatusTypeVO massMailStatusTypeVO)
	{
		// TODO implement loadMassMailStatusTypeFromMassMailStatusTypeVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadMassMailStatusTypeFromMassMailStatusTypeVO(MassMailStatusTypeVO) not yet implemented.");

		MassMailStatusType massMailStatusType = null;
		Long id = massMailStatusTypeVO.getId();
		if (id != null) {
			massMailStatusType = this.load(id);
		}
		if (massMailStatusType == null)
		{
			massMailStatusType = MassMailStatusType.Factory.newInstance();
		}
		return massMailStatusType;

	}


	public MassMailStatusType massMailStatusTypeVOToEntity(MassMailStatusTypeVO massMailStatusTypeVO)
	{
		MassMailStatusType entity = this.loadMassMailStatusTypeFromMassMailStatusTypeVO(massMailStatusTypeVO);
		this.massMailStatusTypeVOToEntity(massMailStatusTypeVO, entity, true);
		return entity;
	}


	@Override
	public void massMailStatusTypeVOToEntity(
			MassMailStatusTypeVO source,
			MassMailStatusType target,
			boolean copyIfNull)
	{

		super.massMailStatusTypeVOToEntity(source, target, copyIfNull);
	}


	public MassMailStatusTypeVO toMassMailStatusTypeVO(final MassMailStatusType entity)
	{

		return super.toMassMailStatusTypeVO(entity);
	}


	public void toMassMailStatusTypeVO(
			MassMailStatusType source,
			MassMailStatusTypeVO target)
	{
		super.toMassMailStatusTypeVO(source, target);
		target.setName(L10nUtil.getMassMailStatusTypeName(Locales.USER, source.getNameL10nKey()));
	}
}