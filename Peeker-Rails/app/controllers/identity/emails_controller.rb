# frozen_string_literal: true

module Identity
  class EmailsController < ApplicationController
    before_action :require_sudo
    before_action :set_user

    def update
      if @user.update(user_params)
        render json: @user
      else
        render json: @user.errors, status: :unprocessable_entity
      end
    end

    private

    def set_user
      @user = Current.user
    end

    def user_params
      params.permit(:email)
    end
  end
end
