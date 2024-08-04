package br.dev.diisk.application.interfaces.dashboard;

import java.time.LocalDateTime;

import br.dev.diisk.application.dtos.SummaryResponse;
import br.dev.diisk.domain.entities.user.User;

public interface IGetSummaryCase {

    SummaryResponse execute(LocalDateTime beginsAt, LocalDateTime endsAt, User user);

}
