FROM hashicorp/terraform:latest

# Copy your config and credentials files (or just config if using IAM role)
COPY .aws /root/.aws

# Set environment variables (optional, but good practice)
ENV AWS_PROFILE=your_profile_name
ENV AWS_DEFAULT_REGION=your_region
RUN apk add --no-cache bash
RUN apk add --no-cache sleep


WORKDIR /app

COPY . /app

CMD ["sleep", "infinity"]