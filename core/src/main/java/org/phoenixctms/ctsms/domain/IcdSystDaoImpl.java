// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.FieldComparator;
import org.phoenixctms.ctsms.util.CommonUtil;
import org.phoenixctms.ctsms.vo.IcdSystBlockVO;
import org.phoenixctms.ctsms.vo.IcdSystCategoryVO;
import org.phoenixctms.ctsms.vo.IcdSystVO;

/**
 * @see IcdSyst
 */
public class IcdSystDaoImpl
extends IcdSystDaoBase
{

	private final static Pattern ICD_CODE_PATTERN_REGEXP = Pattern.compile("^([A-Z])(\\d{2,2})((\\.\\d)(\\d)?)?(\\*|\\+|!)?$");

	private org.hibernate.Criteria createIcdSystCriteria() {
		org.hibernate.Criteria icdSystCriteria = this.getSession().createCriteria(IcdSyst.class);
		return icdSystCriteria;
	}

	@Override
	protected IcdSyst handleFindByIcdCode(String primaryCode, String asteriskCode, String optionalCode, String revision)
	{
		String code = CommonUtil.isEmptyString(primaryCode) ? optionalCode : primaryCode;
		if (!CommonUtil.isEmptyString(code)) {
			Matcher matcher = ICD_CODE_PATTERN_REGEXP.matcher(code);
			if (matcher.find()) {
				StringBuilder search = new StringBuilder(matcher.group(1));
				search.append(matcher.group(2));
				String detail = matcher.group(3);
				if (detail != null && detail.length() > 0) {
					search.append(detail);
				}
				org.hibernate.Criteria icdSystCriteria = createIcdSystCriteria(); // createIcdSystCriteria();
				icdSystCriteria.setCacheable(true);
				icdSystCriteria.add(Restrictions.eq("revision", revision));
				org.hibernate.Criteria categoriesCriteria = icdSystCriteria.createCriteria("categories");
				categoriesCriteria.add(Restrictions.eq("code", search.toString()));
				categoriesCriteria.add(Restrictions.eq("last", true));
				icdSystCriteria.setMaxResults(1);
				return (IcdSyst) icdSystCriteria.uniqueResult();
			}
		}
		return null;
	}

	@Override
	protected long handleGetDiagnosisCount(String revision) throws Exception
	{
		org.hibernate.Criteria icdSystCriteria = createIcdSystCriteria();
		icdSystCriteria.add(Restrictions.eq("revision", revision));
		icdSystCriteria.createCriteria("alphaIds", CriteriaSpecification.INNER_JOIN)
		.createCriteria("diagnoses",CriteriaSpecification.INNER_JOIN);
		return (Long) icdSystCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void handleRemoveAllTxn(Set<IcdSyst> icdSysts) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			Iterator<IcdSyst> it = icdSysts.iterator();
			while (it.hasNext()) {
				removeIcdSyst(it.next().getId());
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(IcdSyst icdSyst) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeIcdSyst(icdSyst.getId());
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(Long icdSystId) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeIcdSyst(icdSystId);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public IcdSyst icdSystVOToEntity(IcdSystVO icdSystVO)
	{
		IcdSyst entity = this.loadIcdSystFromIcdSystVO(icdSystVO);
		this.icdSystVOToEntity(icdSystVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void icdSystVOToEntity(
			IcdSystVO source,
			IcdSyst target,
			boolean copyIfNull)
	{
		super.icdSystVOToEntity(source, target, copyIfNull);
		Collection blocks = source.getBlocks();
		if (blocks.size() > 0) {
			this.getIcdSystBlockDao().icdSystBlockVOToEntityCollection(blocks); // possiby dont work..
			source.setBlocks(blocks);
		} else if (copyIfNull) {
			target.getBlocks().clear();
		}
		Collection categories = source.getCategories();
		if (categories.size() > 0) {
			this.getIcdSystCategoryDao().icdSystCategoryVOToEntityCollection(categories);
			source.setCategories(categories);
		} else if (copyIfNull) {
			target.getCategories().clear();
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private IcdSyst loadIcdSystFromIcdSystVO(IcdSystVO icdSystVO)
	{
		// TODO implement loadIcdSystFromIcdSystVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadIcdSystFromIcdSystVO(IcdSystVO) not yet implemented.");
		Long id = icdSystVO.getId();
		IcdSyst icdSyst = null;
		if (id != null) {
			icdSyst = this.load(id);
		}
		if (icdSyst == null)
		{
			icdSyst = IcdSyst.Factory.newInstance();
		}
		return icdSyst;
	}

	private void removeIcdSyst(Long icdSystId) {
		IcdSyst icdSyst = get(icdSystId);
		this.getHibernateTemplate().deleteAll(icdSyst.getAlphaIds()); // constraint error if used by diagnosis
		icdSyst.getAlphaIds().clear();
		// Long alphaIdsIt = icdSyst.getAlphaIds().iterator();
		// while (alphaIdsIt.hasNext()) {
		// Long alphaId = alphaIdsIt.next();
		// alphaId.setSystematics(null);
		// this.getHibernateTemplate().update(alphaId);
		// }
		// opsSyst.getAlphaIds().clear();
		Iterator<IcdSystCategory> it = icdSyst.getCategories().iterator();
		while (it.hasNext()) {
			this.getHibernateTemplate().deleteAll(it.next().getModifiers());
		}
		this.getHibernateTemplate().deleteAll(icdSyst.getCategories());
		this.getHibernateTemplate().deleteAll(icdSyst.getBlocks());
		this.getHibernateTemplate().delete(icdSyst);
	}

	private ArrayList<IcdSystBlockVO> toIcdSystBlockVOCollection(Collection<IcdSystBlock> blocks) {
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		IcdSystBlockDao icdSystBlockDao = this.getIcdSystBlockDao();
		ArrayList<IcdSystBlockVO> result = new ArrayList<IcdSystBlockVO>(blocks.size());
		Iterator<IcdSystBlock> it = blocks.iterator();
		while (it.hasNext()) {
			result.add(icdSystBlockDao.toIcdSystBlockVO(it.next()));
		}
		Collections.sort(result, new FieldComparator(false, "getLevel"));
		return result;
	}

	private ArrayList<IcdSystCategoryVO> toIcdSystCategoryVOCollection(Collection<IcdSystCategory> categories) {
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		IcdSystCategoryDao icdSystCategoryDao = this.getIcdSystCategoryDao();
		ArrayList<IcdSystCategoryVO> result = new ArrayList<IcdSystCategoryVO>(categories.size());
		Iterator<IcdSystCategory> it = categories.iterator();
		while (it.hasNext()) {
			result.add(icdSystCategoryDao.toIcdSystCategoryVO(it.next()));
		}
		Collections.sort(result, new FieldComparator(false, "getLevel"));
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public IcdSystVO toIcdSystVO(final IcdSyst entity)
	{
		return super.toIcdSystVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toIcdSystVO(
			IcdSyst source,
			IcdSystVO target)
	{
		super.toIcdSystVO(source, target);
		// WARNING! No conversion for target.blocks (can't convert source.getBlocks():org.phoenixctms.ctsms.domain.IcdSystBlock to org.phoenixctms.ctsms.vo.IcdSystBlockVO
		// WARNING! No conversion for target.categories (can't convert source.getCategories():org.phoenixctms.ctsms.domain.IcdSystCategory to org.phoenixctms.ctsms.vo.IcdSystCategoryVO
		target.setBlocks(toIcdSystBlockVOCollection(source.getBlocks()));
		target.setCategories(toIcdSystCategoryVOCollection(source.getCategories()));
	}
}