package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	void doGoodReaction(int actorId, String relTypeCode, int relId);

	void decreaseGoodReaction(int actorId, String relTypeCode, int relId);

	void doBadReaction(int actorId, String relTypeCode, int relId);

	void decreaseBadReaction(int actorId, String relTypeCode, int relId);

	int isAlreadyPoint(int actorId, String relTypeCode, int relId);

}
