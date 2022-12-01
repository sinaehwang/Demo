package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReactionPointRepository;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;

	@Autowired
	private ArticleService articleService;

	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		if(actorId==0) {
			return ResultData.from("F-1","로그인후 이용해주시기 바랍니다.");
		}

		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {

			return ResultData.from("F-2", "기존 추천버튼을 취소후 사용가능", "sumReactionPointByMemberId",sumReactionPointByMemberId
					);

		}

		return ResultData.from("S-1", "추천수 실행가능", "sumReactionPointByMemberId",sumReactionPointByMemberId);

	}

	public void doGoodReaction(int actorId, String relTypeCode, int relId) {

		reactionPointRepository.doGoodReaction(actorId, relTypeCode, relId);

		if (relTypeCode.equals("article")) {

			articleService.increaseGoodReaction(actorId, relId);

		}

	}

	public void decreaseGoodReaction(int actorId, String relTypeCode, int relId) {

		reactionPointRepository.decreaseGoodReaction(actorId, relTypeCode, relId);

		if (relTypeCode.equals("article")) {

			articleService.decreaseGoodReaction(actorId, relId);

		}

	}

	public void doBadReaction(int actorId, String relTypeCode, int relId) {

		reactionPointRepository.doBadReaction(actorId, relTypeCode, relId);

		if (relTypeCode.equals("article")) {

			articleService.increaseBadReaction(actorId, relId);

		}

	}

	public void decreaseBadReaction(int actorId, String relTypeCode, int relId) {

		reactionPointRepository.decreaseBadReaction(actorId, relTypeCode, relId);

		if (relTypeCode.equals("article")) {

			articleService.decreaseBadReaction(actorId, relId);

		}
	}

	public int isAlreadyPoint(int actorId, String relTypeCode, int relId) {

		return reactionPointRepository.isAlreadyPoint(actorId, relTypeCode, relId);
	}

}
