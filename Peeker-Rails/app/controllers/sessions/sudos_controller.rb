# frozen_string_literal: true

module Sessions
  class SudosController < ApplicationController
    def create
      session = Current.session

      if session.user.authenticate(params[:password])
        session.sudo.mark
      else
        render json: { error: 'The password you entered is incorrect' }, status: :bad_request
      end
    end
  end
end
